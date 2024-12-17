package vn.edu.stu.apptruyentranh.api.reponse;

import java.io.Serializable;

public class DanhDauTrang implements Serializable {
    public DanhDauTrang(Integer danhDauTrangId, String nguoiDungId, String chuongTruyenId) {
        this.danhDauTrangId = danhDauTrangId;
        this.nguoiDungId = nguoiDungId;
        this.chuongTruyenId = chuongTruyenId;
    }

    public DanhDauTrang() {
    }

    public Integer getDanhDauTrangId() {
        return danhDauTrangId;
    }

    public void setDanhDauTrangId(Integer danhDauTrangId) {
        this.danhDauTrangId = danhDauTrangId;
    }

    public String getNguoiDungId() {
        return nguoiDungId;
    }

    public void setNguoiDungId(String nguoiDungId) {
        this.nguoiDungId = nguoiDungId;
    }

    public String getChuongTruyenId() {
        return chuongTruyenId;
    }

    public void setChuongTruyenId(String chuongTruyenId) {
        this.chuongTruyenId = chuongTruyenId;
    }

    private Integer danhDauTrangId;
    private String nguoiDungId;
    private String chuongTruyenId;
}
