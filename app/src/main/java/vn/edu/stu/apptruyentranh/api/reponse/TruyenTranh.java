package vn.edu.stu.apptruyentranh.api.reponse;

public class TruyenTranh {
    private int truyenTranhId;
    private String tenTruyen;
    private String theLoai;
    private String tacGia;
    private String anhBia;
    private String trangThai;
    private String moTa;

    // Getters và setters
    public int getTruyenTranhId() {
        return truyenTranhId;
    }

    public void setTruyenTranhId(int truyenTranhId) {
        this.truyenTranhId = truyenTranhId;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getAnhBia() {
        return anhBia;
    }

    public void setAnhBia(String anhBia) {
        this.anhBia = anhBia;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
