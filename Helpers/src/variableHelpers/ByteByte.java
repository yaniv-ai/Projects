package variableHelpers;

public class ByteByte {
	private byte byteAsByte;
	private String byteAsString;
	private char letter;

	public ByteByte(byte byteAsByte) {
		setByteAsByte(byteAsByte);
	}

	public ByteByte(String byteAsString) {
		setByteAsString(byteAsString);
	}

	public ByteByte(ByteByte byteByte) {
		this.byteAsByte = byteByte.getByteAsByte();
		this.byteAsString = new String(byteByte.getByteAsString());
		this.letter = byteByte.getLetter();
	}

	/**
	 * Parses the string argument as an array of 8 {@code bits}. Argument must
	 * consist of the digits 0 and 1 only.
	 * 
	 * @param byteAsString
	 *            a {@code String} containing the {@code byte} representation to
	 *            be parsed
	 * @return the {@code byte} value represented by the argument in bits
	 * @throws NumberFormatException
	 *             if the string does not contain a parsable {@code byte}.
	 */
	private static byte parseByte(String byteAsString) {
		if (valid(byteAsString)) {
			int intbyte = 0;
			for (int j = 0, i = byteAsString.length() - 1; j < byteAsString
					.length(); i--, j++) {
				Character c = byteAsString.charAt(i);
				intbyte += (Integer.parseInt(c.toString()))
						* (Math.pow(2., (double) j));
			}
			return (byte) intbyte;

		} else {
			throw new NumberFormatException(
					"The string does not represent a byte format");
		}
	}

	private static boolean valid(String byteAsString) {
		if (byteAsString == null || byteAsString.length() != 8) {
			return false;
		}
		for (int i = 0; i < byteAsString.length(); i++) {
			if (byteAsString.charAt(0) == '0' || byteAsString.charAt(0) == '1') {
			} else {
				return false;
			}
		}
		return true;
	}

	public void setByteAsByte(byte byteAsByte) {
		this.byteAsByte = byteAsByte;
		this.byteAsString = Integer.toBinaryString(
				(int) (0x000000ff & byteAsByte)).toString();
		switch (byteAsString.length()) {
		case 1:
			byteAsString = "0000000" + byteAsString;
			break;
		case 2:
			byteAsString = "000000" + byteAsString;
			break;
		case 3:
			byteAsString = "00000" + byteAsString;
			break;
		case 4:
			byteAsString = "0000" + byteAsString;
			break;
		case 5:
			byteAsString = "000" + byteAsString;
			break;
		case 6:
			byteAsString = "00" + byteAsString;
			break;
		case 7:
			byteAsString = "0" + byteAsString;
			break;
		default:
			break;
		}
		this.letter = (char) (byteAsByte & 0xFF);
	}

	public void setByteAsString(String byteAsString) {
		setByteAsByte(ByteByte.parseByte(byteAsString));
	}

	public byte getByteAsByte() {
		return byteAsByte;
	}

	public String getByteAsString() {
		return new String(byteAsString);
	}

	public char getLetter() {
		return letter;
	}

}
