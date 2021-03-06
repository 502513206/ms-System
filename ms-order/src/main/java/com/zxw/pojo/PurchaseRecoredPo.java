package com.zxw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author zxw
 * @date 2019/9/3 15:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_purchase_record")
public class PurchaseRecoredPo {
    private static final long serialVersionUID = 328831114776063574L;
    @TableId(type = IdType.AUTO)
    private int id;
    private int user_id;
    private int product_id;
    private double price;
    private int quantity;
    private double sum;
    private Timestamp purchase_date;
    private String note;
}
