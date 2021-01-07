package vo.frame;

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

public class MP3FrameHeader {
	private MP3Synchronizer synchronizer = null;
	private MPEGVersion version = null;
	private MP3Layer layer = null;
	private MP3CRCProtection crcProtection = null;
	private MP3BitrateIndex bitrateIndex = null;
	private MP3SamplingRateFrequencyIndex samplingRateFrequencyIndex = null;
	private MP3Padding padding = null;
	private MP3PrivateBit privateBit = null;
	private MP3Channel channel = null;
	private MP3ModeExtension modeExtension = null;
	private MP3CopyRight copyRight = null;
	private MP3Original original = null;
	private MP3Emphasis emphasis = null;

	public MP3FrameHeader(MP3Synchronizer synchronizer, MPEGVersion version,
			MP3Layer layer, MP3CRCProtection crcProtection,
			MP3BitrateIndex bitrateIndex,
			MP3SamplingRateFrequencyIndex samplingRateFrequencyIndex,
			MP3Padding padding, MP3PrivateBit privateBit, MP3Channel channel,
			MP3ModeExtension modeExtension, MP3CopyRight copyRight,
			MP3Original original, MP3Emphasis emphasis) {
		super();
		this.synchronizer = synchronizer;
		this.version = version;
		this.layer = layer;
		this.crcProtection = crcProtection;
		this.bitrateIndex = bitrateIndex;
		this.samplingRateFrequencyIndex = samplingRateFrequencyIndex;
		this.padding = padding;
		this.privateBit = privateBit;
		this.channel = channel;
		this.modeExtension = modeExtension;
		this.copyRight = copyRight;
		this.original = original;
		this.emphasis = emphasis;
	}

	public MP3Synchronizer getSynchronizer() {
		return synchronizer;
	}

	public void setSynchronizer(MP3Synchronizer synchronizer) {
		this.synchronizer = synchronizer;
	}

	public MPEGVersion getVersion() {
		return version;
	}

	public void setVersion(MPEGVersion version) {
		this.version = version;
	}

	public MP3Layer getLayer() {
		return layer;
	}

	public void setLayer(MP3Layer layer) {
		this.layer = layer;
	}

	public MP3CRCProtection getCrcProtection() {
		return crcProtection;
	}

	public void setCrcProtection(MP3CRCProtection crcProtection) {
		this.crcProtection = crcProtection;
	}

	public MP3BitrateIndex getBitrateIndex() {
		return bitrateIndex;
	}

	public void setBitrateIndex(MP3BitrateIndex bitrateIndex) {
		this.bitrateIndex = bitrateIndex;
	}

	public MP3SamplingRateFrequencyIndex getSamplingRateFrequencyIndex() {
		return samplingRateFrequencyIndex;
	}

	public void setSamplingRateFrequencyIndex(
			MP3SamplingRateFrequencyIndex samplingRateFrequencyIndex) {
		this.samplingRateFrequencyIndex = samplingRateFrequencyIndex;
	}

	public MP3Padding getPadding() {
		return padding;
	}

	public void setPadding(MP3Padding padding) {
		this.padding = padding;
	}

	public MP3PrivateBit getPrivateBit() {
		return privateBit;
	}

	public void setPrivateBit(MP3PrivateBit privateBit) {
		this.privateBit = privateBit;
	}

	public MP3Channel getChannel() {
		return channel;
	}

	public void setChannel(MP3Channel channel) {
		this.channel = channel;
	}

	public MP3ModeExtension getModeExtension() {
		return modeExtension;
	}

	public void setModeExtension(MP3ModeExtension modeExtension) {
		this.modeExtension = modeExtension;
	}

	public MP3CopyRight getCopyRight() {
		return copyRight;
	}

	public void setCopyRight(MP3CopyRight copyRight) {
		this.copyRight = copyRight;
	}

	public MP3Original getOriginal() {
		return original;
	}

	public void setOriginal(MP3Original original) {
		this.original = original;
	}

	public MP3Emphasis getEmphasis() {
		return emphasis;
	}

	public void setEmphasis(MP3Emphasis emphasis) {
		this.emphasis = emphasis;
	}

}
