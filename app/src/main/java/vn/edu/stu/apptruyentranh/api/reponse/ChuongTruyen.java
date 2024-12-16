package vn.edu.stu.apptruyentranh.api.reponse;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChuongTruyen implements Serializable {
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

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date releaseDate = formatter.parse(ngayPhatHanh);
            Date currentDate = new Date();
            if (releaseDate.after(currentDate)) {
                return String.format("%-30s %s", tenChuong, "chưa phát hành");
            } else {
                return String.format("%-30s %s", tenChuong, "phát hành: " + ngayPhatHanh);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return tenChuong;
        }
    }
}