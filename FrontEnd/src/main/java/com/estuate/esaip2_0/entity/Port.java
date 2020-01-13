package com.estuate.esaip2_0.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

/**
 * <ul>
 * <li>Title: Port</li>
 * <li>Description: The Port entity</li>
 * <li>Created By: Radhika GopalRaj</li>
 * </ul>
 */
@Entity
@Component
@Table(name = "est_userport")
@SequenceGenerator(name = "port_id", initialValue = 3000)
public class Port extends SuperEntity {
/**
 * The port
 */
	@Id
	@GenericGenerator(name="myIdGen",strategy="com.estuate.esaip2_0.model.MyIdGenerator")
	@GeneratedValue(generator = "myIdGen", strategy = GenerationType.AUTO)
	@Column(name = "Port")
	private int port;
	/**
	 * The user
	 */
	@OneToOne
	@JoinColumn(name = "UserID")
	private UserDetail user;

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the user
	 */
	public UserDetail getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserDetail user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Port [port=" + port + ", user=" + user + "]";
	}
}
