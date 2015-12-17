package cn.yesway.demo.codec;

import java.io.Serializable;

import io.netty.util.internal.chmv8.ForkJoinTask;
/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年11月26日 下午1:56:47 
 * 
 */
public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private int age;
	private int type;
	private String adress;
	private String hobby; //爱好
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Student [id="+id+" name=" + name + ", age=" + age + ", type=" + type
				+ ", adress=" + adress + ", hobby=" + hobby + "]";
	}
	
}
