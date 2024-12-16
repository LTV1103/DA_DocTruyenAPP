package vn.edu.stu.apptruyentranh.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.edu.stu.apptruyentranh.api.reponse.ChuongTruyen;
import vn.edu.stu.apptruyentranh.api.reponse.NguoiDung;
import vn.edu.stu.apptruyentranh.api.reponse.TruyenTranh;

public interface APIService {
    @GET("/api/truyentranh")
    Call<List<TruyenTranh>> getAllComics();

    @GET("/api/truyentranh/{id}")
    Call<TruyenTranh> getComicById(@Path("id") int id);

    @GET("/api/chuongtruyen/truyentranh/{id}")
    Call<List<ChuongTruyen>> getChaptersByTruyenTranhId(@Path("id") int id);

    @GET("/api/chuongtruyen/image/{id}")
    Call<List<String>> getChapterImages(@Path("id") int id);

    @GET("/api/nguoidung/tenDangNhap/{tenDangNhap}")
    Call<NguoiDung> getUserByTenDangNhap(@Path("tenDangNhap") String tenDangNhap);

    @GET("/api/truyentranh/theloai")
    Call<List<String>> getAllCategories();


    @POST("/api/nguoidung")
    Call<NguoiDung> saveUser(@Body NguoiDung user);

    @POST("/api/nguoidung/create")
    Call<NguoiDung> createUser(@Body NguoiDung user);


    @GET("/api/truyentranh/theloai/{theLoai}")
    Call<List<TruyenTranh>> searchTruyenByCategory(@Path("theLoai") String category);












}
