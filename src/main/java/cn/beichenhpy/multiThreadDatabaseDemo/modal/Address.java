package cn.beichenhpy.multiThreadDatabaseDemo.modal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author hanpengyu
 * @version 1.0
 * @apiNote
 * @since 2021/12/15 16:40
 */
@TableName(value = "address")
@Data
@SuperBuilder
public class Address {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String address;

    private String name;
}
