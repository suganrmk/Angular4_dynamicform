package com.perficient.hr.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "roles_components")
@SuppressWarnings("serial")
public class RolesComponents extends AbstractModel {

	@Id
	@GeneratedValue
	@Column(name = "pk", length = 11)
	private Long pk;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "roles_pk")
	private Roles role;
	
	@Transient
	private List<Employee> employee;
	
	@Column(name = "admMn")
	private boolean admMn;
	
	@Column(name = "jtPg")
	private boolean jtPg;
	
	@Column(name = "jtSvBtn")
	private boolean jtSvBtn;
	
	@Column(name = "jtUpBtn")
	private boolean jtUpBtn;
	
	@Column(name = "jtDlBtn")
	private boolean jtDlBtn;
	
	@Column(name = "rlPg")
	private boolean rlPg;
	
	@Column(name = "rlSvBtn")
	private boolean rlSvBtn;
	
	@Column(name = "rlUpBtn")
	private boolean rlUpBtn;
	
	@Column(name = "rlDlBtn")
	private boolean rlDlBtn;
	
	@Column(name = "empPg")
	private boolean empPg;
	
	@Column(name = "empSvBtn")
	private boolean empSvBtn;
	
	@Column(name = "empUpBtn")
	private boolean empUpBtn;
	
	@Column(name = "empDlBtn")
	private boolean empDlBtn;
	
	@Column(name = "erPg")
	private boolean erPg;
	
	@Column(name = "erApBtn")
	private boolean erApBtn;
	
	@Column(name = "hdPg")
	private boolean hdPg;
	
	@Column(name = "hdSvBtn")
	private boolean hdSvBtn;
	
	@Column(name = "hdUpBtn")
	private boolean hdUpBtn;
	
	@Column(name = "hdDlBtn")
	private boolean hdDlBtn;
	
	@Column(name = "mlPg")
	private boolean mlPg;
	
	@Column(name = "prPg")
	private boolean prPg;
	
	@Column(name = "prSvBtn")
	private boolean prSvBtn;
	
	@Column(name = "prUpBtn")
	private boolean prUpBtn;
	
	@Column(name = "prDlBtn")
	private boolean prDlBtn;
	
	@Column(name = "pmPg")
	private boolean pmPg;
	
	@Column(name = "pmSvBtn")
	private boolean pmSvBtn;
	
	@Column(name = "pmUpBtn")
	private boolean pmUpBtn;
	
	@Column(name = "pmDlBtn")
	private boolean pmDlBtn;
	
	@Column(name = "repMn")
	private boolean repMn;
	
	@Column(name = "jtRepPg")
	private boolean jtRepPg;
	
	@Column(name = "ptoRepPg")
	private boolean ptoRepPg;
	
	@Column(name = "wfhRepPg")
	private boolean wfhRepPg;
	
	@Column(name = "mgmtMn")
	private boolean mgmtMn;
	
	@Column(name = "finMn")
	private boolean finMn;
	
	@Column(name = "itruwin")
	private boolean itruwin;
	
	@Column(name = "itrrept")
	private boolean itrrept;
	
	@Column(name = "ebsRepPg")
	private boolean ebsRepPg;
	
	@Column(name = "sdPg")
	private boolean sdPg;
	
	@Column(name = "sdUpBtn")
	private boolean sdUpBtn;

	@Column(name = "tktRepPg")
	private boolean tktRepPg;
	
	@Column(name = "rstpwdpg")
	private boolean rstpwdpg;
	
	@Column(name = "ptoUpBtn")
	private boolean ptoUpBtn;
	
	@Column(name = "ptoDlBtn")
	private boolean ptoDlBtn;
	
	@Column(name = "wfhUpBtn")
	private boolean wfhUpBtn;
	
	@Column(name = "wfhDlBtn")
	private boolean wfhDlBtn;
	
	@Column(name = "taaMn")
	private boolean taaMn;
	
	public HashMap<String, String> getComponents(List<RolesComponents> roleCompList){
		HashMap<String, String> compMap = new HashMap<>();
		for(RolesComponents roleComponent: roleCompList){
			Field fields[] = roleComponent.getClass().getDeclaredFields();
		    for (Field fld : fields){
		    	try {				
		    		if(fld.getType() == boolean.class){
		    			if(Boolean.parseBoolean(fld.get(roleComponent).toString()))
		    				compMap.put(fld.getName(), "0");
		    		}
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
		}
		return compMap;
	}
	
	/**
	 * @return the pk
	 */
	public Long getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(Long pk) {
		this.pk = pk;
	}

	/**
	 * @return the role
	 */
	public Roles getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Roles role) {
		this.role = role;
	}

	/**
	 * @return the employee
	 */
	public List<Employee> getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}

	/**
	 * @return the admMn
	 */
	public boolean isAdmMn() {
		return admMn;
	}

	/**
	 * @param admMn the admMn to set
	 */
	public void setAdmMn(boolean admMn) {
		this.admMn = admMn;
	}

	/**
	 * @return the jtPg
	 */
	public boolean isJtPg() {
		return jtPg;
	}

	/**
	 * @param jtPg the jtPg to set
	 */
	public void setJtPg(boolean jtPg) {
		this.jtPg = jtPg;
	}

	/**
	 * @return the jtSvBtn
	 */
	public boolean isJtSvBtn() {
		return jtSvBtn;
	}

	/**
	 * @param jtSvBtn the jtSvBtn to set
	 */
	public void setJtSvBtn(boolean jtSvBtn) {
		this.jtSvBtn = jtSvBtn;
	}

	/**
	 * @return the jtUpBtn
	 */
	public boolean isJtUpBtn() {
		return jtUpBtn;
	}

	/**
	 * @param jtUpBtn the jtUpBtn to set
	 */
	public void setJtUpBtn(boolean jtUpBtn) {
		this.jtUpBtn = jtUpBtn;
	}

	/**
	 * @return the jtDlBtn
	 */
	public boolean isJtDlBtn() {
		return jtDlBtn;
	}

	/**
	 * @param jtDlBtn the jtDlBtn to set
	 */
	public void setJtDlBtn(boolean jtDlBtn) {
		this.jtDlBtn = jtDlBtn;
	}

	/**
	 * @return the rlPg
	 */
	public boolean isRlPg() {
		return rlPg;
	}

	/**
	 * @param rlPg the rlPg to set
	 */
	public void setRlPg(boolean rlPg) {
		this.rlPg = rlPg;
	}

	/**
	 * @return the rlSvBtn
	 */
	public boolean isRlSvBtn() {
		return rlSvBtn;
	}

	/**
	 * @param rlSvBtn the rlSvBtn to set
	 */
	public void setRlSvBtn(boolean rlSvBtn) {
		this.rlSvBtn = rlSvBtn;
	}

	/**
	 * @return the rlUpBtn
	 */
	public boolean isRlUpBtn() {
		return rlUpBtn;
	}

	/**
	 * @param rlUpBtn the rlUpBtn to set
	 */
	public void setRlUpBtn(boolean rlUpBtn) {
		this.rlUpBtn = rlUpBtn;
	}

	/**
	 * @return the rlDlBtn
	 */
	public boolean isRlDlBtn() {
		return rlDlBtn;
	}

	/**
	 * @param rlDlBtn the rlDlBtn to set
	 */
	public void setRlDlBtn(boolean rlDlBtn) {
		this.rlDlBtn = rlDlBtn;
	}

	/**
	 * @return the empPg
	 */
	public boolean isEmpPg() {
		return empPg;
	}

	/**
	 * @param empPg the empPg to set
	 */
	public void setEmpPg(boolean empPg) {
		this.empPg = empPg;
	}

	/**
	 * @return the empSvBtn
	 */
	public boolean isEmpSvBtn() {
		return empSvBtn;
	}

	/**
	 * @param empSvBtn the empSvBtn to set
	 */
	public void setEmpSvBtn(boolean empSvBtn) {
		this.empSvBtn = empSvBtn;
	}

	/**
	 * @return the empUpBtn
	 */
	public boolean isEmpUpBtn() {
		return empUpBtn;
	}

	/**
	 * @param empUpBtn the empUpBtn to set
	 */
	public void setEmpUpBtn(boolean empUpBtn) {
		this.empUpBtn = empUpBtn;
	}

	/**
	 * @return the empDlBtn
	 */
	public boolean isEmpDlBtn() {
		return empDlBtn;
	}

	/**
	 * @param empDlBtn the empDlBtn to set
	 */
	public void setEmpDlBtn(boolean empDlBtn) {
		this.empDlBtn = empDlBtn;
	}

	/**
	 * @return the erPg
	 */
	public boolean isErPg() {
		return erPg;
	}

	/**
	 * @param erPg the erPg to set
	 */
	public void setErPg(boolean erPg) {
		this.erPg = erPg;
	}

	/**
	 * @return the erApBtn
	 */
	public boolean isErApBtn() {
		return erApBtn;
	}

	/**
	 * @param erApBtn the erApBtn to set
	 */
	public void setErApBtn(boolean erApBtn) {
		this.erApBtn = erApBtn;
	}

	/**
	 * @return the hdPg
	 */
	public boolean isHdPg() {
		return hdPg;
	}

	/**
	 * @param hdPg the hdPg to set
	 */
	public void setHdPg(boolean hdPg) {
		this.hdPg = hdPg;
	}

	/**
	 * @return the hdSvBtn
	 */
	public boolean isHdSvBtn() {
		return hdSvBtn;
	}

	/**
	 * @param hdSvBtn the hdSvBtn to set
	 */
	public void setHdSvBtn(boolean hdSvBtn) {
		this.hdSvBtn = hdSvBtn;
	}

	/**
	 * @return the hdUpBtn
	 */
	public boolean isHdUpBtn() {
		return hdUpBtn;
	}

	/**
	 * @param hdUpBtn the hdUpBtn to set
	 */
	public void setHdUpBtn(boolean hdUpBtn) {
		this.hdUpBtn = hdUpBtn;
	}

	/**
	 * @return the hdDlBtn
	 */
	public boolean isHdDlBtn() {
		return hdDlBtn;
	}

	/**
	 * @param hdDlBtn the hdDlBtn to set
	 */
	public void setHdDlBtn(boolean hdDlBtn) {
		this.hdDlBtn = hdDlBtn;
	}

	/**
	 * @return the mlPg
	 */
	public boolean isMlPg() {
		return mlPg;
	}

	/**
	 * @param mlPg the mlPg to set
	 */
	public void setMlPg(boolean mlPg) {
		this.mlPg = mlPg;
	}

	/**
	 * @return the prPg
	 */
	public boolean isPrPg() {
		return prPg;
	}

	/**
	 * @param prPg the prPg to set
	 */
	public void setPrPg(boolean prPg) {
		this.prPg = prPg;
	}

	/**
	 * @return the prSvBtn
	 */
	public boolean isPrSvBtn() {
		return prSvBtn;
	}

	/**
	 * @param prSvBtn the prSvBtn to set
	 */
	public void setPrSvBtn(boolean prSvBtn) {
		this.prSvBtn = prSvBtn;
	}

	/**
	 * @return the prUpBtn
	 */
	public boolean isPrUpBtn() {
		return prUpBtn;
	}

	/**
	 * @param prUpBtn the prUpBtn to set
	 */
	public void setPrUpBtn(boolean prUpBtn) {
		this.prUpBtn = prUpBtn;
	}

	/**
	 * @return the prDlBtn
	 */
	public boolean isPrDlBtn() {
		return prDlBtn;
	}

	/**
	 * @param prDlBtn the prDlBtn to set
	 */
	public void setPrDlBtn(boolean prDlBtn) {
		this.prDlBtn = prDlBtn;
	}

	/**
	 * @return the pmPg
	 */
	public boolean isPmPg() {
		return pmPg;
	}

	/**
	 * @param pmPg the pmPg to set
	 */
	public void setPmPg(boolean pmPg) {
		this.pmPg = pmPg;
	}

	/**
	 * @return the pmSvBtn
	 */
	public boolean isPmSvBtn() {
		return pmSvBtn;
	}

	/**
	 * @param pmSvBtn the pmSvBtn to set
	 */
	public void setPmSvBtn(boolean pmSvBtn) {
		this.pmSvBtn = pmSvBtn;
	}

	/**
	 * @return the pmUpBtn
	 */
	public boolean isPmUpBtn() {
		return pmUpBtn;
	}

	/**
	 * @param pmUpBtn the pmUpBtn to set
	 */
	public void setPmUpBtn(boolean pmUpBtn) {
		this.pmUpBtn = pmUpBtn;
	}

	/**
	 * @return the pmDlBtn
	 */
	public boolean isPmDlBtn() {
		return pmDlBtn;
	}

	/**
	 * @param pmDlBtn the pmDlBtn to set
	 */
	public void setPmDlBtn(boolean pmDlBtn) {
		this.pmDlBtn = pmDlBtn;
	}

	/**
	 * @return the repMn
	 */
	public boolean isRepMn() {
		return repMn;
	}

	/**
	 * @param repMn the repMn to set
	 */
	public void setRepMn(boolean repMn) {
		this.repMn = repMn;
	}

	/**
	 * @return the jtRepPg
	 */
	public boolean isJtRepPg() {
		return jtRepPg;
	}

	/**
	 * @param jtRepPg the jtRepPg to set
	 */
	public void setJtRepPg(boolean jtRepPg) {
		this.jtRepPg = jtRepPg;
	}

	/**
	 * @return the ptoRepPg
	 */
	public boolean isPtoRepPg() {
		return ptoRepPg;
	}

	/**
	 * @param ptoRepPg the ptoRepPg to set
	 */
	public void setPtoRepPg(boolean ptoRepPg) {
		this.ptoRepPg = ptoRepPg;
	}

	/**
	 * @return the wfhRepPg
	 */
	public boolean isWfhRepPg() {
		return wfhRepPg;
	}

	/**
	 * @param wfhRepPg the wfhRepPg to set
	 */
	public void setWfhRepPg(boolean wfhRepPg) {
		this.wfhRepPg = wfhRepPg;
	}

	/**
	 * @return the mgmtMn
	 */
	public boolean isMgmtMn() {
		return mgmtMn;
	}

	/**
	 * @param mgmtMn the mgmtMn to set
	 */
	public void setMgmtMn(boolean mgmtMn) {
		this.mgmtMn = mgmtMn;
	}

	/**
	 * @return the finMn
	 */
	public boolean isFinMn() {
		return finMn;
	}

	/**
	 * @param finMn the finMn to set
	 */
	public void setFinMn(boolean finMn) {
		this.finMn = finMn;
	}

	/**
	 * @return the itruwin
	 */
	public boolean isItruwin() {
		return itruwin;
	}

	/**
	 * @param itruwin the itruwin to set
	 */
	public void setItruwin(boolean itruwin) {
		this.itruwin = itruwin;
	}

	/**
	 * @return the itrrept
	 */
	public boolean isItrrept() {
		return itrrept;
	}

	/**
	 * @param itrrept the itrrept to set
	 */
	public void setItrrept(boolean itrrept) {
		this.itrrept = itrrept;
	}

	/**
	 * @return the ebsRepPg
	 */
	public boolean isEbsRepPg() {
		return ebsRepPg;
	}

	/**
	 * @param ebsRepPg the ebsRepPg to set
	 */
	public void setEbsRepPg(boolean ebsRepPg) {
		this.ebsRepPg = ebsRepPg;
	}

	/**
	 * @return the sdPg
	 */
	public boolean isSdPg() {
		return sdPg;
	}

	/**
	 * @param sdPg the sdPg to set
	 */
	public void setSdPg(boolean sdPg) {
		this.sdPg = sdPg;
	}

	/**
	 * @return the sdUpBtn
	 */
	public boolean isSdUpBtn() {
		return sdUpBtn;
	}

	/**
	 * @param sdUpBtn the sdUpBtn to set
	 */
	public void setSdUpBtn(boolean sdUpBtn) {
		this.sdUpBtn = sdUpBtn;
	}

	/**
	 * @return the tktRepPg
	 */
	public boolean isTktRepPg() {
		return tktRepPg;
	}

	/**
	 * @param tktRepPg the tktRepPg to set
	 */
	public void setTktRepPg(boolean tktRepPg) {
		this.tktRepPg = tktRepPg;
	}

	/**
	 * @return the rstpwdpg
	 */
	public boolean isRstpwdpg() {
		return rstpwdpg;
	}

	/**
	 * @param rstpwdpg the rstpwdpg to set
	 */
	public void setRstpwdpg(boolean rstpwdpg) {
		this.rstpwdpg = rstpwdpg;
	}

	/**
	 * @return the ptoUpBtn
	 */
	public boolean isPtoUpBtn() {
		return ptoUpBtn;
	}

	/**
	 * @param ptoUpBtn the ptoUpBtn to set
	 */
	public void setPtoUpBtn(boolean ptoUpBtn) {
		this.ptoUpBtn = ptoUpBtn;
	}

	/**
	 * @return the ptoDlBtn
	 */
	public boolean isPtoDlBtn() {
		return ptoDlBtn;
	}

	/**
	 * @param ptoDlBtn the ptoDlBtn to set
	 */
	public void setPtoDlBtn(boolean ptoDlBtn) {
		this.ptoDlBtn = ptoDlBtn;
	}

	/**
	 * @return the wfhUpBtn
	 */
	public boolean isWfhUpBtn() {
		return wfhUpBtn;
	}

	/**
	 * @param wfhUpBtn the wfhUpBtn to set
	 */
	public void setWfhUpBtn(boolean wfhUpBtn) {
		this.wfhUpBtn = wfhUpBtn;
	}

	/**
	 * @return the wfhDlBtn
	 */
	public boolean isWfhDlBtn() {
		return wfhDlBtn;
	}

	/**
	 * @param wfhDlBtn the wfhDlBtn to set
	 */
	public void setWfhDlBtn(boolean wfhDlBtn) {
		this.wfhDlBtn = wfhDlBtn;
	}

	/**
	 * @return the taaMn
	 */
	public boolean isTaaMn() {
		return taaMn;
	}

	/**
	 * @param taaMn the taaMn to set
	 */
	public void setTaaMn(boolean taaMn) {
		this.taaMn = taaMn;
	}
	
}