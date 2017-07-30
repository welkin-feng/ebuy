package com.welkin.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp;

@Service
public class ImageUploaderService {
	@Value("${imageNginx.username}")
	private String username;
	@Value("${imageNginx.host}")
	private String host;
	private int port = 22;
	@Value("${imageNginx.userpwd}")
	private String userpwd;
	@Value("${imageNginx.baseDir}")
	private String baseDir;

	/**
	 * 功能 通过该sftp上传的图片服务器
	 * @param uploadfile 客户端上传的本地文件
	 * @param url 向客户端返回的上传后的文件的图片服务器上的路径
	 * 
	 */
	public boolean upload(MultipartFile uploadfile, StringBuilder url) {
		// 初始化jsch组件
		JSch ch = new JSch();
		// getSession获取回话（与服务器连接），返回连接的回话
		Session session = null;
		ChannelSftp channel = null;
		boolean flag = false;
		try {
			System.out.println("sftp地址:" + host);
			session = ch.getSession(username, host, port);
			// 设置连接密码
			session.setPassword(userpwd);
			// 设置连接属性
			Properties p = new Properties();
			p.setProperty("StrictHostKeyChecking", "no");
			// 设置连接的回话属性
			session.setConfig(p);
			// 通过会话连接服务器
			session.connect();
			System.out.println("连接成功!");
			// 通过回话对象得到一个通道
			channel = (ChannelSftp) session.openChannel("sftp");
			// 通过通道连接远程服务器
			channel.connect();

			// 获取上传文件的扩展名
			String ext = getFileExt(uploadfile);
			System.out.println("上传文件的扩展名：" + ext);
			StringBuilder dir = new StringBuilder();
			// 在服务器上生成新的上传路径
			String uploadFilename = getRandomName(dir, ext);

			try {
				// 通过通道完成ftp相关操作
				// 插入操作
				// 在服务器上生成上传目录
				channel.mkdir(baseDir + "/" + dir.toString());
			} catch (Exception e) {

			}
			// 通过通过对象上传文件
			channel.put(uploadfile.getInputStream(), baseDir + "/" + dir + "/" + uploadFilename);
			// 完成上传
			String accessUrl = "http://" + host + "/" + dir + "/" + uploadFilename;
			// 将上传完成的url写入到调用bean
			url.append(accessUrl);
			System.out.println("服务器访问路径" + accessUrl);
			flag = true;

		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			channel.disconnect();
			session.disconnect();
		}
		return flag;
	}

	/* 得到上传后的文件名称 */
	private String getRandomName(StringBuilder dir, String ext) {
		long ctime = System.currentTimeMillis();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		String d = sim.format(new Date());
		dir.append(d);

		return ctime + ext;
	}

	private String getFileExt(MultipartFile uploadfile) {
		String ofilename = uploadfile.getOriginalFilename();// 得到上传的源文件名
		return ofilename.substring(ofilename.lastIndexOf("."));
	}
}
