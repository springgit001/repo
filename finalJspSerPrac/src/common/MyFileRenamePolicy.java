package common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MyFileRenamePolicy implements FileRenamePolicy{
	@Override
	public File rename(File originFile) {
		
		long currentTime = System.currentTimeMillis();
		
		SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmmss");
		int ranNum = (int)(Math.random()*100000);
		
		//확장자 명 가져오기
		String name = originFile.getName();
		String ext = null;
		
		int dot = name.lastIndexOf(".");
		
		if(dot != -1) {
			//dot 포함해서 확장자 추출(ext)
			ext = name.substring(dot);
		}else {
			//확장자가 없는 경우
			ext = "";
		}
		
		String fileName = ft.format(new Date(currentTime)) + ranNum + ext;
		File newFile = new File(originFile.getParent(), fileName);
		
		return newFile;
	}
}
