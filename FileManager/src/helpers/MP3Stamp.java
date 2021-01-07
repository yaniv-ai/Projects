package helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeoutException;

import variableHelpers.ByteByte;
import vo.frame.MP3FrameHeader;
import vo.frame.header.MP3BitrateIndex;
import vo.frame.header.MP3CRCProtection;
import vo.frame.header.MP3Channel;
import vo.frame.header.MP3CopyRight;
import vo.frame.header.MP3Emphasis;
import vo.frame.header.MP3Layer;
import vo.frame.header.MP3ModeExtension;
import vo.frame.header.MP3Original;
import vo.frame.header.MP3Padding;
import vo.frame.header.MP3PrivateBit;
import vo.frame.header.MP3SamplingRateFrequencyIndex;
import vo.frame.header.MP3Synchronizer;
import vo.frame.header.MPEGVersion;
import exceptions.HeaderNotValidException;
import fileHelpers.FlexibleFileArray;

public class MP3Stamp extends FileStamp {

	public final static int BYTES_AFTER_HEADER_FOR_STAMP = 30;
	public final static int MP3_STAMP_MAX_SIZE = 4;
	private static final byte FIRST_BYTE = -1;
	private final Long NEXT_FRAME_NOT_FOUND = -1l;
	private static final int HEADER_BYTE_AMOUNT = 4;
	private static final int ID3_TAG1_LENGTH = 128;
	private static final String ID3_TAG1 = "TAG";
	private static final String ID3_TAG2 = "ID3";
	private boolean enforceMP3Check = false;
	private int frameErrorRange = 10;

	private int mp3FrameAmount = 0;
	private String mp3Signature = null;
	private FlexibleFileArray flexibleFileArray;

	private Long firstFrameIndex = null;
	private Long lastFrameIndex = null;

	public MP3Stamp(FlexibleFileArray flexibleFileArray, int stampAmount, boolean enforceMP3Check)
			throws IOException, TimeoutException, HeaderNotValidException {
		super(flexibleFileArray, stampAmount);
		this.flexibleFileArray = flexibleFileArray;
		init();
		if (flexibleFileArray == null || flexibleFileArray.size() == 0) {
			return;
		}
		this.enforceMP3Check = enforceMP3Check;
		init();
		firstFrameIndex = findFirstFrameIndex();
		if (firstFrameIndex != null && firstFrameIndex >= 0) {
			lastFrameIndex = findLastFrameIndex();
			extractMP3Stamp(firstFrameIndex, lastFrameIndex);
		}
	}

	public MP3Stamp(MP3Stamp mp3Stamp, int stampAmount) {
		super(mp3Stamp, stampAmount);
		init();
		this.mp3FrameAmount = mp3Stamp.getMp3FrameAmount();
		this.mp3Signature = mp3Stamp.getMp3Signature();
	}

	private void init() {
		mp3FrameAmount = 0;
		mp3Signature = "NOTMP3";
	}

	private long findFirstFrameIndex() throws IOException, TimeoutException, HeaderNotValidException {
		boolean fileStartsWithID3 = false;
		char[] id3Tag = new char[3];
		if (flexibleFileArray.size() > 3) {
			id3Tag[0] = (char) flexibleFileArray.get(0);
			id3Tag[1] = (char) flexibleFileArray.get(1);
			id3Tag[2] = (char) flexibleFileArray.get(2);
			String tag = new String(id3Tag);
			if (tag != null && tag.equals(ID3_TAG2)) {
				fileStartsWithID3 = true;
			}
		}
		if (enforceMP3Check) {
			return calculateFirstFrameIndex();
		} else {
			if (fileStartsWithID3) {
				return calculateFirstFrameIndex();
			} else {
				boolean check = checkIfNextFrameIsValid(0l);
				if (check) {
					return 0l;
				} else {
					return -1l;
				}
			}
		}
	}

	private long calculateFirstFrameIndex() throws IOException, TimeoutException, HeaderNotValidException {
		long len = flexibleFileArray.size() - 4l;
		for (long i = 0; i < len; i++) {
			byte b = flexibleFileArray.get(i);
			if (b == FIRST_BYTE) {
				if (checkIfNextFrameIsValid(i)) {
					MP3FrameHeader frameHeader = extractFrameHeader(i);
					int calculatedFrameLength = calculateFrameLength(frameHeader);
					if (getNextFrameIndex(calculatedFrameLength, i) > -1l) {
						return i;
					}
				}
			}
		}
		return NEXT_FRAME_NOT_FOUND;
	}

	private long findLastFrameIndex() throws IOException, TimeoutException {
		long lastByteIndex = flexibleFileArray.size();
		long maybeTag = flexibleFileArray.size() - ID3_TAG1_LENGTH;

		if (maybeTag > 0) {
			char[] tag = new char[3];
			tag[0] = (new ByteByte(flexibleFileArray.get(maybeTag))).getLetter();
			tag[1] = (new ByteByte(flexibleFileArray.get(maybeTag + 1))).getLetter();
			tag[2] = (new ByteByte(flexibleFileArray.get(maybeTag + 2))).getLetter();
			String s = new String(tag);
			if (s.equals(ID3_TAG1)) {
				lastByteIndex = maybeTag;
			}
		}
		return lastByteIndex;
	}

	private boolean checkIfNextFrameIsValid(long l) throws IOException, TimeoutException {
		try {
			extractFrameHeader(l);
			return true;
		} catch (HeaderNotValidException e) {
			return false;
		}
	}

	private long getNextFrameIndex(int calculatedFrameLength, long i) throws IOException, TimeoutException {
		boolean foundNextFrame = false;
		long nextFrameIndex = flexibleFileArray.size();

		for (long j = 0; j < frameErrorRange && !foundNextFrame; j++) {
			for (int k = 0; k < 2 && !foundNextFrame; k++) {
				long sign;
				if (k == 0) {
					sign = 1l;
				} else {
					sign = -1l;
				}
				long tempNextFrameIndex = i + (j * sign) + (long) calculatedFrameLength;
				if (tempNextFrameIndex == flexibleFileArray.size()) {
					foundNextFrame = true;
					nextFrameIndex = tempNextFrameIndex;
				}
				if (tempNextFrameIndex < flexibleFileArray.size()) {
					if (tempNextFrameIndex < flexibleFileArray.size() - 3) {
						char[] tag = new char[3];
						tag[0] = (new ByteByte(flexibleFileArray.get(tempNextFrameIndex))).getLetter();
						tag[1] = (new ByteByte(flexibleFileArray.get(tempNextFrameIndex + 1))).getLetter();
						tag[2] = (new ByteByte(flexibleFileArray.get(tempNextFrameIndex + 2))).getLetter();
						String s = new String(tag);
						if (s != null && s.equals(ID3_TAG1)) {
							foundNextFrame = true;
							nextFrameIndex = tempNextFrameIndex;
						}
					}
					if (flexibleFileArray.get(tempNextFrameIndex) == FIRST_BYTE) {
						boolean check = checkIfNextFrameIsValid(tempNextFrameIndex);
						if (check) {
							foundNextFrame = true;
							nextFrameIndex = tempNextFrameIndex;
						}
					}
				}
			}
		}
		return nextFrameIndex;
	}

	private void extractMP3Stamp(long start, long end) throws IOException, TimeoutException, HeaderNotValidException {
		ArrayList<Long> frameIndexArray = new ArrayList<Long>();
		boolean endOfMP3Content = false;
		while (start < end - 4l && !endOfMP3Content) {
			frameIndexArray.add(start);
			if (checkIfNextFrameIsValid(start)) {
				MP3FrameHeader frameHeader = extractFrameHeader(start);
				int calculatedFrameLength = calculateFrameLength(frameHeader);
				start = getNextFrameIndex(calculatedFrameLength, start);
			} else {
				endOfMP3Content = true;
			}
		}
		setMp3FrameAmount(frameIndexArray.size());
		setMp3Signature(frameIndexArray);
	}

	public Long getFirstFrameIndex() {
		return lastFrameIndex;
	}

	public Long getLastFrameIndex() {
		return lastFrameIndex;
	}

	private MP3FrameHeader extractFrameHeader(long i) throws HeaderNotValidException, IOException, TimeoutException {
		if (i > flexibleFileArray.size() - HEADER_BYTE_AMOUNT) {
			throw new HeaderNotValidException("Header must have a length of 4 bytes (16 characters)");
		}
		String header = (new ByteByte(flexibleFileArray.get(i))).getByteAsString()
				+ (new ByteByte(flexibleFileArray.get(i + 1))).getByteAsString()
				+ (new ByteByte(flexibleFileArray.get(i + 2))).getByteAsString()
				+ (new ByteByte(flexibleFileArray.get(i + 3))).getByteAsString();

		MP3FrameHeader frameHeader = null;
		String synchronizer = header.substring(0, 11);
		String version = header.substring(11, 13);
		String layer = header.substring(13, 15);
		String crcProtection = header.substring(15, 16);
		String bitrateIndex = header.substring(16, 20);
		String samplingRateFrequencyIndex = header.substring(20, 22);
		String padding = header.substring(22, 23);
		String privateBit = header.substring(23, 24);
		String channel = header.substring(24, 26);
		String modeExtension = header.substring(26, 28);
		String copyRight = header.substring(28, 29);
		String original = header.substring(29, 30);
		String emphasis = header.substring(30, 32);
		try {
			MP3Synchronizer mp3Synchronizer = getMP3Synchronizer(synchronizer);
			MPEGVersion mpegVersion = getMPEGVersion(version);
			MP3Layer mp3Layer = getMP3Layer(layer);
			MP3CRCProtection mp3crcProtection = getMP3CRCProtection(crcProtection);
			MP3BitrateIndex mp3BitrateIndex = getMP3BitrateIndex(bitrateIndex);
			MP3SamplingRateFrequencyIndex mp3SamplingRateFrequencyIndex = getMP3SamplingRateFrequencyIndex(
					samplingRateFrequencyIndex);
			MP3Padding mp3Padding = getMP3Padding(padding);
			MP3PrivateBit mp3PrivateBit = getMP3PrivateBit(privateBit);
			MP3Channel mp3Channel = getMP3Channel(channel);
			MP3ModeExtension mp3ModeExtension = getMP3ModeExtension(modeExtension);
			MP3CopyRight mp3CopyRight = getMP3CopyRight(copyRight);
			MP3Original mp3Original = getMP3Original(original);
			MP3Emphasis mp3Emphasis = getMP3Emphasis(emphasis);

			frameHeader = new MP3FrameHeader(mp3Synchronizer, mpegVersion, mp3Layer, mp3crcProtection, mp3BitrateIndex,
					mp3SamplingRateFrequencyIndex, mp3Padding, mp3PrivateBit, mp3Channel, mp3ModeExtension,
					mp3CopyRight, mp3Original, mp3Emphasis);
		} catch (IllegalArgumentException e) {
			throw new HeaderNotValidException("Content is not a valid header");
		} catch (NullPointerException e) {
			throw new HeaderNotValidException("Content is not a valid header");
		} catch (HeaderNotValidException e) {
			throw e;
		}

		return frameHeader;
	}

	public int getMp3FrameAmount() {
		return mp3FrameAmount;
	}

	private void setMp3FrameAmount(int mp3FrameAmount) {
		this.mp3FrameAmount = mp3FrameAmount;
	}

	public String getMp3Signature() {
		return mp3Signature;
	}

	private void setMp3Signature(ArrayList<Long> frameIndexArray) throws IOException, TimeoutException {
		if (frameIndexArray == null || frameIndexArray.size() == 0) {
			return;
		}
		int gap = frameIndexArray.size() / STAMP_AMOUNT;
		ArrayList<ByteByte> mp3StampArray = new ArrayList<ByteByte>(STAMP_AMOUNT);
		for (int i = 0; i < STAMP_AMOUNT && i < flexibleFileArray.size(); i++) {
			long index = frameIndexArray.get(gap * i) + BYTES_AFTER_HEADER_FOR_STAMP;
			if (index < flexibleFileArray.size()) {
				mp3StampArray.add(new ByteByte(flexibleFileArray.get(index)));
			}
		}

		if (mp3StampArray != null && mp3StampArray.size() > 0) {
			mp3Signature = "";
			Iterator<ByteByte> i = mp3StampArray.iterator();
			while (i.hasNext()) {
				byte b = (byte) (mod26(i.next().getByteAsByte()) + 64);
				mp3Signature = mp3Signature + (char) (b & 0xFF);
			}
		}

	}

	private int calculateFrameLength(MP3FrameHeader frameHeader) throws HeaderNotValidException {
		if (frameHeader == null) {
			throw new HeaderNotValidException("Frame header is null");
		}
		int frameLength = (int) ((144 * frameHeader.getBitrateIndex().getBitRateAsInt() * 1000
				/ frameHeader.getSamplingRateFrequencyIndex().getSamplingRateFrequencyIndexAsInt())
				+ frameHeader.getPadding().getPaddingAsInt());

		return frameLength;
	}

	public MP3Synchronizer getMP3Synchronizer(String synchronizer) throws HeaderNotValidException {
		if (MP3Synchronizer.Synchronizer.getSinchronizer().equals(synchronizer)) {
			return MP3Synchronizer.Synchronizer;
		}
		throw new HeaderNotValidException("Synchronizer not valid");
	}

	public MPEGVersion getMPEGVersion(String version) throws HeaderNotValidException {
		if (MPEGVersion.Reserved.getVersion().equals(version)) {
			return MPEGVersion.Reserved;
		}
		if (MPEGVersion.V1.getVersion().equals(version)) {
			return MPEGVersion.V1;
		}
		if (MPEGVersion.V2.getVersion().equals(version)) {
			return MPEGVersion.V2;
		}
		if (MPEGVersion.V2_5.getVersion().equals(version)) {
			return MPEGVersion.V2_5;
		}
		throw new HeaderNotValidException("Version not valid");
	}

	public MP3Layer getMP3Layer(String layer) throws HeaderNotValidException {
		if (MP3Layer.Layer_I.getLayer().equals(layer)) {
			return MP3Layer.Layer_I;
		}
		if (MP3Layer.Layer_II.getLayer().equals(layer)) {
			return MP3Layer.Layer_II;
		}
		if (MP3Layer.Layer_III.getLayer().equals(layer)) {
			return MP3Layer.Layer_III;
		}
		if (MP3Layer.Reserved.getLayer().equals(layer)) {
			return MP3Layer.Reserved;
		}
		throw new HeaderNotValidException("Layer not valid");
	}

	public MP3CRCProtection getMP3CRCProtection(String crcProtection) throws HeaderNotValidException {
		if (MP3CRCProtection.Not_Protected.getCRCProtection().equals(crcProtection)) {
			return MP3CRCProtection.Not_Protected;
		}
		if (MP3CRCProtection.Protected.getCRCProtection().equals(crcProtection)) {
			return MP3CRCProtection.Protected;
		}
		throw new HeaderNotValidException("CRC protection not valid");
	}

	public MP3BitrateIndex getMP3BitrateIndex(String bitrateIndex) throws HeaderNotValidException {
		if (MP3BitrateIndex.Kbps32.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kbps32;
		}
		if (MP3BitrateIndex.Kpbs112.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs112;
		}
		if (MP3BitrateIndex.Kpbs128.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs128;
		}
		if (MP3BitrateIndex.Kpbs160.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs160;
		}
		if (MP3BitrateIndex.Kpbs192.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs192;
		}
		if (MP3BitrateIndex.Kpbs224.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs224;
		}
		if (MP3BitrateIndex.Kpbs256.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs256;
		}
		if (MP3BitrateIndex.Kpbs320.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs320;
		}
		if (MP3BitrateIndex.Kpbs40.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs40;
		}
		if (MP3BitrateIndex.Kpbs48.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs48;
		}
		if (MP3BitrateIndex.Kpbs56.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs56;
		}
		if (MP3BitrateIndex.Kpbs64.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs64;
		}
		if (MP3BitrateIndex.Kpbs80.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs80;
		}
		if (MP3BitrateIndex.Kpbs96.getBitRate().equals(bitrateIndex)) {
			return MP3BitrateIndex.Kpbs96;
		}
		throw new HeaderNotValidException("Bitrate index not valid");
	}

	public MP3SamplingRateFrequencyIndex getMP3SamplingRateFrequencyIndex(String samplingRateFrequencyIndex)
			throws HeaderNotValidException {
		if (MP3SamplingRateFrequencyIndex.Hz32000.getSamplingRateFrequencyIndex().equals(samplingRateFrequencyIndex)) {
			return MP3SamplingRateFrequencyIndex.Hz32000;
		}
		if (MP3SamplingRateFrequencyIndex.Hz44100.getSamplingRateFrequencyIndex().equals(samplingRateFrequencyIndex)) {
			return MP3SamplingRateFrequencyIndex.Hz44100;
		}
		if (MP3SamplingRateFrequencyIndex.Hz48000.getSamplingRateFrequencyIndex().equals(samplingRateFrequencyIndex)) {
			return MP3SamplingRateFrequencyIndex.Hz48000;
		}
		throw new HeaderNotValidException("Sampling rate frequency not valid");
	}

	public MP3Padding getMP3Padding(String padding) throws HeaderNotValidException {
		if (MP3Padding.Not_Padded.getPadding().equals(padding)) {
			return MP3Padding.Not_Padded;
		}
		if (MP3Padding.Padded.getPadding().equals(padding)) {
			return MP3Padding.Padded;
		}

		throw new HeaderNotValidException("Padding not valid");
	}

	public MP3PrivateBit getMP3PrivateBit(String privateBit) throws HeaderNotValidException {
		if (MP3PrivateBit.One.getPrivateBit().equals(privateBit)) {
			return MP3PrivateBit.One;
		}
		if (MP3PrivateBit.Zero.getPrivateBit().equals(privateBit)) {
			return MP3PrivateBit.Zero;
		}
		throw new HeaderNotValidException("Private bit not valid");
	}

	public MP3Channel getMP3Channel(String channel) throws HeaderNotValidException {
		if (MP3Channel.Dual.getChannel().equals(channel)) {
			return MP3Channel.Dual;
		}
		if (MP3Channel.JointS_Stereo.getChannel().equals(channel)) {
			return MP3Channel.JointS_Stereo;
		}
		if (MP3Channel.Mono.getChannel().equals(channel)) {
			return MP3Channel.Mono;
		}
		if (MP3Channel.Stereo.getChannel().equals(channel)) {
			return MP3Channel.Stereo;
		}
		throw new HeaderNotValidException("Chanel not valid");
	}

	public MP3ModeExtension getMP3ModeExtension(String modeExtension) throws HeaderNotValidException {
		if (MP3ModeExtension.OffOff.getModeExtension().equals(modeExtension)) {
			return MP3ModeExtension.OffOff;
		}
		if (MP3ModeExtension.OffOn.getModeExtension().equals(modeExtension)) {
			return MP3ModeExtension.OffOn;
		}
		if (MP3ModeExtension.OnOff.getModeExtension().equals(modeExtension)) {
			return MP3ModeExtension.OnOff;
		}
		if (MP3ModeExtension.OnOn.getModeExtension().equals(modeExtension)) {
			return MP3ModeExtension.OnOn;
		}
		throw new HeaderNotValidException("Mode extension not valid");
	}

	public MP3CopyRight getMP3CopyRight(String copyRight) throws HeaderNotValidException {
		if (MP3CopyRight.Copyrighted.getCopyright().equals(copyRight)) {
			return MP3CopyRight.Copyrighted;
		}
		if (MP3CopyRight.Not_Copyrighted.getCopyright().equals(copyRight)) {
			return MP3CopyRight.Not_Copyrighted;
		}
		throw new HeaderNotValidException("Copyright not valid");
	}

	public MP3Original getMP3Original(String original) throws HeaderNotValidException {
		if (MP3Original.Copy.getOriginalMedia().equals(original)) {
			return MP3Original.Copy;
		}
		if (MP3Original.Original.getOriginalMedia().equals(original)) {
			return MP3Original.Original;
		}
		throw new HeaderNotValidException("Original not valid");
	}

	public MP3Emphasis getMP3Emphasis(String emphasis) throws HeaderNotValidException {
		if (MP3Emphasis.CCIT_j_17.getEmphasis().equals(emphasis)) {
			return MP3Emphasis.CCIT_j_17;
		}
		if (MP3Emphasis.None.getEmphasis().equals(emphasis)) {
			return MP3Emphasis.None;
		}
		if (MP3Emphasis.E50_15.getEmphasis().equals(emphasis)) {
			return MP3Emphasis.E50_15;
		}
		throw new HeaderNotValidException("Emphasis not valid");
	}

//	public static void main(String[] args) {
//		File file1 = new File("C:\\A\\Test\\MVI_0593.AVI");
//		FlexibleFileArray flexibleFileArray = new FlexibleFileArray();
//		try {
//			flexibleFileArray.loadFile(file1);
//			MP3Stamp stamp1 = new MP3Stamp(flexibleFileArray, 10, false);
//			System.out.println(stamp1.getFileSignature());
//			System.out.println(stamp1.getMp3FrameAmount());
//			System.out.println(stamp1.getMp3Signature());
//		} catch (IOException | TimeoutException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//	}
}
