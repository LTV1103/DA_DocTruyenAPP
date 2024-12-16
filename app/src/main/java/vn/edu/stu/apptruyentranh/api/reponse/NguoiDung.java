package vn.edu.stu.apptruyentranh.api.reponse;

import java.io.Serializable;
import java.math.BigDecimal;

public class NguoiDung implements Serializable {
    private int nguoiDungId;
    private String tenDangNhap;
    private String email;
    private String matKhau;
    private BigDecimal soDu;
    private PhanQuyen phanQuyen;
    public enum PhanQuyen {
        USER,ADMIN;
    }

    public static NguoiDung nowUser;

    public NguoiDung() {
        this.phanQuyen = PhanQuyen.USER;
    }

    public NguoiDung(int nguoiDungId, String tenDangNhap, String email, String matKhau, BigDecimal soDu) {
        this.nguoiDungId = nguoiDungId;
        this.tenDangNhap = tenDangNhap;
        this.email = email;
        this.matKhau = matKhau;
        this.soDu = soDu;
        this.phanQuyen = PhanQuyen.USER;
    }

    public int getNguoiDungId() {
        return nguoiDungId;
    }

    public void setNguoiDungId(int nguoiDungId) {
        this.nguoiDungId = nguoiDungId;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public BigDecimal getSoDu() {
        return soDu;
    }

    public void setSoDu(BigDecimal soDu) {
        this.soDu = soDu;
    }
}
