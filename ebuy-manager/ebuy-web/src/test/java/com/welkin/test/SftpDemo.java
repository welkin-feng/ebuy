package com.welkin.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

import org.junit.Test;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SftpDemo {

	/* 远程浏览sftp服务器上目录 */
	@Test
	public void serverFileList() throws JSchException, IOException {
		JSch jsch = new JSch();

		ChannelSftp channel = null;

		String username = "ftpuser";
		String host = "10.211.55.7";
		int port = 22;
		String userpwd = "ftpuser";

		try {
			// getSession 获取会话（与服务器连接），返回连接的会话
			Session session = jsch.getSession(username, host, port);
			// 设置连接密码
			session.setPassword(userpwd);
			// 设置连接属性
			Properties p = new Properties();
			p.setProperty("StrictHostKeyChecking", "no");
			// 设置连接的会话属性
			session.setConfig(p);
			// 通过会话连接服务器
			session.connect();
			System.out.println("连接成功");
			// 通过会话对象得到一个通道
			channel = (ChannelSftp) session.openChannel("sftp");
			// 通过通道连接远程服务器
			channel.connect();
			// 通过通道完成ftp相关操作
			// 查看指定服务器目录
			// LsEntry 解析浏览的文件
			/*Vector<LsEntry> vc = channel.ls("/");
			for (LsEntry en : vc) {
				String filename = en.getFilename();
				String longname = en.getLongname();
				System.out.println("f: " + filename + ", l" + longname);

			}*/
			
			InputStream rd = new FileInputStream(new File("/Users/Welkin/Pictures/screenCut.png"));
			
			channel.put(rd, "/home/ftpuser/www/screenCut.png");
			System.out.println("上传成功");
			rd.close();
					

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			channel.disconnect();
		}

	}

}