package com.zdhx.androidbase.util.imageLoader.util;


public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "Hzth_IM_File/image/";
		} else {
			return CommonUtil.getRootFilePath() + "Hzth_IM_File/image";
		}
	}
}
