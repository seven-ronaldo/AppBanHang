package hao.com.appbanhang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import hao.com.appbanhang.Interface.IImageClickListener;
import hao.com.appbanhang.R;
import hao.com.appbanhang.model.EventBus.TinhTongEvent;
import hao.com.appbanhang.model.GioHang;
import hao.com.appbanhang.utils.Utils;
import io.paperdb.Paper;

public class    GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;
    private CheckBox checkedAll;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTensp());
        holder.item_giohang_soluong.setText(gioHang.getSoluong() + "");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText(decimalFormat.format(gioHang.getGiasp()));
        Glide.with(context)
                .load(gioHang.getHinhsp())
                .into(holder.item_giohang_image);
        long gia = gioHang.getSoluong() * gioHang.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Utils.manggiohang.get(holder.getAdapterPosition()).setChecked(true);
                    if (!Utils.mangmuahang.contains(gioHang)) {
                        Utils.mangmuahang.add(gioHang);
                    }
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                } else {
                    Utils.manggiohang.get(holder.getAdapterPosition()).setChecked(false);
                    for (int i = 0; i < Utils.mangmuahang.size(); i++){
                        if (Utils.mangmuahang.get(i).getIdsp() == gioHang.getIdsp()){
                            Utils.mangmuahang.remove(i);
                            EventBus.getDefault().postSticky(new TinhTongEvent());
                        }
                    }
                }
            }
        });

        holder.checkBox.setChecked(gioHang.isChecked());

        holder.setListener(new IImageClickListener() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG", "onImageClick" + pos + " ..." + giatri);
                if (giatri == 1){
                    if (gioHangList.get(pos).getSoluong() > 1){
                        int soluongmoi = gioHangList.get(pos).getSoluong() - 1;
                        gioHangList.get(pos).setSoluong(soluongmoi);

                        holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() + "");
                        long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    } else if (gioHangList.get(pos).getSoluong() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng? ");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.mangmuahang.remove(gioHang);
                                Utils.manggiohang.remove(pos);
                                Paper.book().write("giohang", Utils.manggiohang);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                } else if (giatri == 2) {
                    if (gioHangList.get(pos).getSoluong() < gioHangList.get(pos).getSltonkho()){
                        int soluongmoi = gioHangList.get(pos).getSoluong() + 1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }
                    holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() + "");
                    long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }
            }
        });

    }

    public void updateAllCheckbox(boolean isChecked) {

        boolean isAllChecked = true; // Giả sử ban đầu tất cả các mặt hàng đều được chọn

        // Nếu checkbox "Chọn tất cả" được chọn
        if (isChecked) {
            // Duyệt qua tất cả các mặt hàng trong danh sách
            for (GioHang gioHang : gioHangList) {
                // Đặt trạng thái checked của mỗi mặt hàng là true
                gioHang.setChecked(true);
                // Kiểm tra xem mặt hàng đã được thêm vào danh sách mua hàng chưa
                if (!Utils.mangmuahang.contains(gioHang)) {
                    // Nếu chưa, thêm vào danh sách mua hàng
                    Utils.mangmuahang.add(gioHang);
                }
            }
        } else {
            // Nếu checkbox "Chọn tất cả" không được chọn
            // Duyệt qua tất cả các mặt hàng trong danh sách
            for (GioHang gioHang : gioHangList) {
                // Đặt trạng thái checked của mỗi mặt hàng là false
                gioHang.setChecked(false);
                // Kiểm tra xem mặt hàng có trong danh sách mua hàng không
                for (int i = 0; i < Utils.mangmuahang.size(); i++) {
                    if (Utils.mangmuahang.get(i).getIdsp() == gioHang.getIdsp()) {
                        // Nếu có, loại bỏ mặt hàng khỏi danh sách mua hàng
                        Utils.mangmuahang.remove(i);
                        // Sau khi loại bỏ, thoát khỏi vòng lặp
                        break;
                    }
                }
            }
            isAllChecked = false; // Có ít nhất một mặt hàng không được chọn
        }

        // Thông báo cho adapter rằng dữ liệu đã thay đổi
        notifyDataSetChanged();
        // Gửi sự kiện để tính tổng tiền mới
        EventBus.getDefault().postSticky(new TinhTongEvent());

        // Đặt trạng thái của checkbox "Chọn tất cả" dựa trên isAllChecked
        //checkedAll.setChecked(isAllChecked);
    }



    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image, imgtru, imgcong;
        TextView item_giohang_tensp, item_giohang_gia, item_giohang_soluong, item_giohang_giasp2;
        IImageClickListener listener;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_gia2);
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);
            checkBox = itemView.findViewById(R.id.item_giohang_check);
            checkedAll = itemView.findViewById(R.id.checkedAll);

            //event click
            imgtru.setOnClickListener(this);
            imgcong.setOnClickListener(this);
        }

        public void setListener(IImageClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if (v == imgtru){
                listener.onImageClick(v, getAdapterPosition(), 1);
            } else if (v == imgcong){
                listener.onImageClick(v, getAdapterPosition(), 2);
            }
        }
    }
}
