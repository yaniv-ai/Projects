package vo;

public class Mp3Vo extends FileVo {
	private String id3v1Songname;
	private String id3v1Artist;
	private String id3v1Album;
	private String id3v1Year;
	private String id3v1Comment;
	private String id3v2Songname;
	private String id3v2Artist;
	private String id3v2Album;
	private String id3v2Year;
	private String id3v2Comment;
	private Byte genre;
	private Boolean overide;

	public Mp3Vo() {
		super();
		id3v1Songname = "";
		id3v1Artist = "";
		id3v1Album = "";
		id3v1Year = "";
		id3v1Comment = "";
		id3v2Songname = "";
		id3v2Artist = "";
		id3v2Album = "";
		id3v2Year = "";
		id3v2Comment = "";
		genre = (byte) 0;
		overide = false;
	}

	public Mp3Vo(String dir, String fileName, String extension, String fullPathName, String fullDirectory,
			String fileSignature, String mp3Signature, Integer mp3FrameAmount, Long processTime, Long lastModified,
			Long length, Integer fileGroupID, String id3v1Songname, String id3v1Artist, String id3v1Album,
			String id3v1Year, String id3v1Comment, String id3v2Songname, String id3v2Artist, String id3v2Album,
			String id3v2Year, String id3v2Comment, byte genre, boolean overide) {
		super(dir, fileName, extension, fullPathName, fullDirectory, fileSignature, mp3Signature, mp3FrameAmount,
				processTime, lastModified, length, fileGroupID);
		this.id3v1Songname = id3v1Songname;
		this.id3v1Artist = id3v1Artist;
		this.id3v1Album = id3v1Album;
		this.id3v1Year = id3v1Year;
		this.id3v1Comment = id3v1Comment;
		this.id3v2Songname = id3v2Songname;
		this.id3v2Artist = id3v2Artist;
		this.id3v2Album = id3v2Album;
		this.id3v2Year = id3v2Year;
		this.id3v2Comment = id3v2Comment;
		this.genre = genre;
		this.overide = overide;
	}

	public Mp3Vo(Mp3Vo mp3Vo) {
		super(mp3Vo);
		this.id3v1Songname = mp3Vo.getId3v1Songname();
		this.id3v1Artist = mp3Vo.getId3v1Artist();
		this.id3v1Album = mp3Vo.getId3v1Album();
		this.id3v1Year = mp3Vo.getId3v1Year();
		this.id3v1Comment = mp3Vo.getId3v1Comment();
		this.id3v2Songname = mp3Vo.getId3v2Songname();
		this.id3v2Artist = mp3Vo.getId3v2Artist();
		this.id3v2Album = mp3Vo.getId3v2Album();
		this.id3v2Year = mp3Vo.getId3v2Year();
		this.id3v2Comment = mp3Vo.getId3v2Comment();
		this.genre = mp3Vo.getGenre();
		this.overide = mp3Vo.getOveride();
	}

	public String getId3v1Songname() {
		return id3v1Songname;
	}

	public void setId3v1Songname(String id3v1Songname) {
		this.id3v1Songname = id3v1Songname;
	}

	public String getId3v1Artist() {
		return id3v1Artist;
	}

	public void setId3v1Artist(String id3v1Artist) {
		this.id3v1Artist = id3v1Artist;
	}

	public String getId3v1Album() {
		return id3v1Album;
	}

	public void setId3v1Album(String id3v1Album) {
		this.id3v1Album = id3v1Album;
	}

	public String getId3v1Year() {
		return id3v1Year;
	}

	public void setId3v1Year(String id3v1Year) {
		this.id3v1Year = id3v1Year;
	}

	public String getId3v1Comment() {
		return id3v1Comment;
	}

	public void setId3v1Comment(String id3v1Comment) {
		this.id3v1Comment = id3v1Comment;
	}

	public String getId3v2Songname() {
		return id3v2Songname;
	}

	public void setId3v2Songname(String id3v2Songname) {
		this.id3v2Songname = id3v2Songname;
	}

	public String getId3v2Artist() {
		return id3v2Artist;
	}

	public void setId3v2Artist(String id3v2Artist) {
		this.id3v2Artist = id3v2Artist;
	}

	public String getId3v2Album() {
		return id3v2Album;
	}

	public void setId3v2Album(String id3v2Album) {
		this.id3v2Album = id3v2Album;
	}

	public String getId3v2Year() {
		return id3v2Year;
	}

	public void setId3v2Year(String id3v2Year) {
		this.id3v2Year = id3v2Year;
	}

	public String getId3v2Comment() {
		return id3v2Comment;
	}

	public void setId3v2Comment(String id3v2Comment) {
		this.id3v2Comment = id3v2Comment;
	}

	public Byte getGenre() {
		return genre;
	}

	public void setGenre(Byte genre) {
		this.genre = genre;
	}

	public Boolean getOveride() {
		return overide;
	}

	public void setOveride(Boolean overide) {
		this.overide = overide;
	}

	public void setTagsOnly(Mp3Vo keyMp3Vo) {
		setId3v1Artist(keyMp3Vo.getId3v1Artist());
		setId3v2Artist(keyMp3Vo.getId3v2Artist());
		setId3v1Songname(keyMp3Vo.getId3v1Songname());
		setId3v2Songname(keyMp3Vo.getId3v2Songname());
		setId3v1Album(keyMp3Vo.getId3v1Album());
		setId3v2Album(keyMp3Vo.getId3v2Album());
		setId3v1Year(keyMp3Vo.getId3v1Year());
		setId3v2Year(keyMp3Vo.getId3v2Year());
	}

}
