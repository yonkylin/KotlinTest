package yonky.kotlintest.mvp.model.bean
import java.io.Serializable
/**
 * Created by Administrator on 2018/6/25.
 */
data class CategoryBean(val id: Long,
                        val name: String,
                        val description: String,
                        val bgPicture: String,
                        val bgColor: String,
                        val headerImage: String) : Serializable