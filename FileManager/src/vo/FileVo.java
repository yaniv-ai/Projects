package vo;

public class FileVo implements Comparable<FileVo> {
	private String drive;
	private String fileName;
	private String extension;
	private String fullPathName;
	private String fullDirectory;
	private String fileSignature;
	private String mp3Signature;
	private Integer mp3FrameAmount;
	private Long processTime;
	private Long lastModified;
	private Long length;
	private Integer fileGroupID;

	public FileVo() {
		this("", "", "", "", "", "", "", 0, 0l, 0l, 0l, -1);
	}

	public FileVo(FileVo fileVo) {
		this(fileVo.getDrive(), fileVo.getFileName(), fileVo.getExtension(), fileVo.getFullPathName(),
				fileVo.getFullDirectory(), fileVo.getFileSignature(), fileVo.getMp3Signature(),
				fileVo.getMp3FrameAmount(), fileVo.getProcessTime(), fileVo.getLastModified(), fileVo.getLength(),
				fileVo.getFileGroupID());
	}

	public FileVo(String drive, String fileName, String extension, String fullPathName, String fullDirectory,
			String fileSignature, String mp3Signature, Integer mp3FrameAmount, Long processTime, Long lastModified,
			Long length, Integer fileGroupID) {
		super();
		this.drive = drive;
		this.fileName = fileName;
		this.extension = extension;
		this.fullPathName = fullPathName;
		this.fullDirectory = fullDirectory;
		this.fileSignature = fileSignature;
		this.mp3Signature = mp3Signature;
		this.mp3FrameAmount = mp3FrameAmount;
		this.processTime = processTime;
		this.lastModified = lastModified;
		this.length = length;
		this.fileGroupID = fileGroupID;

	}

	public String getDrive() {
		return drive;
	}

	public void setDrive(String fileName) {
		this.drive = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFullPathName() {
		return fullPathName;
	}

	public void setFullPathName(String fullPathName) {
		this.fullPathName = fullPathName;
	}

	public String getFullDirectory() {
		return fullDirectory;
	}

	public void setFullDirectory(String fullDirectory) {
		this.fullDirectory = fullDirectory;
	}

	public String getFileSignature() {
		return fileSignature;
	}

	public void setFileSignature(String fileSignature) {
		this.fileSignature = fileSignature;
	}

	public String getMp3Signature() {
		return mp3Signature;
	}

	public void setMp3Signature(String mp3Signature) {
		this.mp3Signature = mp3Signature;
	}

	public Integer getMp3FrameAmount() {
		return mp3FrameAmount;
	}

	public void setMp3FrameAmount(Integer mp3FrameAmount) {
		this.mp3FrameAmount = mp3FrameAmount;
	}

	public Long getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Long processTime) {
		this.processTime = processTime;
	}

	public Long getLastModified() {
		return lastModified;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public Integer getFileGroupID() {
		return fileGroupID;
	}

	public void setFileGroupID(Integer fileGroupID) {
		this.fileGroupID = fileGroupID;
	}

	@Override
	public int compareTo(FileVo o) {
		if (length.compareTo(o.getLength()) < 0) {
			return -50;
		} else if (length.compareTo(o.getLength()) == 0) {
			if (fileSignature.compareTo(o.getFileSignature()) < 0) {
				return -40;
			} else if (fileSignature.compareTo(o.getFileSignature()) == 0) {
				if (drive.compareTo(o.getDrive()) > 0) {
					return -30;
				} else if (drive.compareTo(o.getDrive()) == 0) {
					if (fullDirectory.compareTo(o.getFullDirectory()) < 0) {
						return -20;
					} else if (fullDirectory.compareTo(o.getFullDirectory()) == 0) {
						return 0;
					} else
						return 20;
				} else
					return 30;
			} else {
				return 40;
			}
		} else {
			return 50;
		}
	}

}
