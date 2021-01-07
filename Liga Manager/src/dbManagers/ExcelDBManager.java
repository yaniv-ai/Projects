package dbManagers;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import liga.Liga;

import vo.GroupVO;

public class ExcelDBManager {

	public ExcelDBManager() {
	}

	public Vector<GroupVO> getExcelGroups() throws IOException {
		Vector<GroupVO> groupList = new Vector<GroupVO>();
		File in = new File(Liga.groupsFileName);

		BufferedReader bufferedIn = null;
		try {
			bufferedIn = new BufferedReader(new FileReader(in));
			String groupName = bufferedIn.readLine();
			while (groupName != null) {
				if (groupName.length() > 0) {
					groupList.add(new GroupVO(null, groupName, null, null,
							null, null));
				}
				groupName = bufferedIn.readLine();
			}

			return groupList;
		} catch (FileNotFoundException e) {
			return groupList;
		} catch (EOFException e) {
			return groupList;
		} catch (IOException e) {
			throw e;
		} finally {
			if (bufferedIn != null)
				try {
					bufferedIn.close();
				} catch (IOException e) {
					throw e;
				}
		}
	}
}
