package vn.edu.stu.apptruyentranh.api.reponse;

public class ChuongTruyen {
    private int chuongTruyenId;
    private String truyenTranhId;
    private String tenChuong;
    private String ngayPhatHanh;
    private String hinhAnhTruyen;

    public ChuongTruyen() {
    }

    public int getChuongTruyenId() {
        return chuongTruyenId;
    }

    public void setChuongTruyenId(int chuongTruyenId) {
        this.chuongTruyenId = chuongTruyenId;
    }

    public String getTruyenTranhId() {
        return truyenTranhId;
    }

    public void setTruyenTranhId(String truyenTranhId) {
        this.truyenTranhId = truyenTranhId;
    }

    public String getTenChuong() {
        return tenChuong;
    }

    public void setTenChuong(String tenChuong) {
        this.tenChuong = tenChuong;
    }

    public String getNgayPhatHanh() {
        return ngayPhatHanh;
    }

    public void setNgayPhatHanh(String ngayPhatHanh) {
        this.ngayPhatHanh = ngayPhatHanh;
    }

    public String getHinhAnhTruyen() {
        return hinhAnhTruyen;
    }

    public void setHinhAnhTruyen(String hinhAnhTruyen) {
        this.hinhAnhTruyen = hinhAnhTruyen;
    }
}
