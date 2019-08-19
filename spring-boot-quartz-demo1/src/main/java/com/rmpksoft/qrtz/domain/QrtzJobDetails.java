package com.rmpksoft.intro.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "qrtz_job_details", catalog = "quartz", schema = "")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "QrtzJobDetails.findAll", query = "SELECT q FROM QrtzJobDetails q"),
		@NamedQuery(name = "QrtzJobDetails.findBySchedName", query = "SELECT q FROM QrtzJobDetails q WHERE q.qrtzJobDetailsPK.schedName = :schedName"),
		@NamedQuery(name = "QrtzJobDetails.findByJobName", query = "SELECT q FROM QrtzJobDetails q WHERE q.qrtzJobDetailsPK.jobName = :jobName"),
		@NamedQuery(name = "QrtzJobDetails.findByJobGroup", query = "SELECT q FROM QrtzJobDetails q WHERE q.qrtzJobDetailsPK.jobGroup = :jobGroup"),
		@NamedQuery(name = "QrtzJobDetails.findByDescription", query = "SELECT q FROM QrtzJobDetails q WHERE q.description = :description"),
		@NamedQuery(name = "QrtzJobDetails.findByJobClassName", query = "SELECT q FROM QrtzJobDetails q WHERE q.jobClassName = :jobClassName"),
		@NamedQuery(name = "QrtzJobDetails.findByIsDurable", query = "SELECT q FROM QrtzJobDetails q WHERE q.isDurable = :isDurable"),
		@NamedQuery(name = "QrtzJobDetails.findByIsNonconcurrent", query = "SELECT q FROM QrtzJobDetails q WHERE q.isNonconcurrent = :isNonconcurrent"),
		@NamedQuery(name = "QrtzJobDetails.findByIsUpdateData", query = "SELECT q FROM QrtzJobDetails q WHERE q.isUpdateData = :isUpdateData"),
		@NamedQuery(name = "QrtzJobDetails.findByRequestsRecovery", query = "SELECT q FROM QrtzJobDetails q WHERE q.requestsRecovery = :requestsRecovery") })

public class QrtzJobDetails implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected QrtzJobDetailsPK qrtzJobDetailsPK;
	@Size(max = 250)
	@Column(length = 250)
	private String description;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 250)
	@Column(name = "JOB_CLASS_NAME", nullable = false, length = 250)
	private String jobClassName;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 1)
	@Column(name = "IS_DURABLE", nullable = false, length = 1)
	private String isDurable;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 1)
	@Column(name = "IS_NONCONCURRENT", nullable = false, length = 1)
	private String isNonconcurrent;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 1)
	@Column(name = "IS_UPDATE_DATA", nullable = false, length = 1)
	private String isUpdateData;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 1)
	@Column(name = "REQUESTS_RECOVERY", nullable = false, length = 1)
	private String requestsRecovery;
	@Lob
	@Column(name = "JOB_DATA")
	private byte[] jobData;
	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "qrtzJobDetails")
	//private List<QrtzTriggers> qrtzTriggersList;

}
