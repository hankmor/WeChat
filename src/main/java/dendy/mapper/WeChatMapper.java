package dendy.mapper;

import dendy.domain.WeChatMediaMaterial;
import dendy.domain.WeChatKeyWords;
import dendy.domain.WechatUserBind;
import dendy.domain.menu.WeChatMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeChatMapper {
    /**
     * 新增菜单项
     */
    void createMenu(WeChatMenu menu);

    /**
     * 查询微信菜单信息
     */
    List<WeChatMenu> findAllMenuEnable(@Param(value = "menu_enable") String menu_enable);

    /**
     * 创建微信关键字信息
     */
    void createKeyWords(WeChatKeyWords kw);

    /**
     * 删除关键字回复
     *
     * @return void
     */
    void deleteKeyWords(Long wc_id);

    /**
     * 查询符合关键字的key-words  数字关键字不可以重复
     * 通过数字key 获取
     *
     * @return 返回符合key关键字的对象列表
     */
    WeChatKeyWords findKeyWordsByKey(@Param(value = "wc_key_num") Long wc_key_num);

    /**
     * 通过用户输入的中文内容进行获取关键字列表
     *
     * @return 返回符合中文描述的关键字内容对象列表
     */
    List<WeChatKeyWords> findKeyWordsByCN(String wc_key_cn);

    /**
     * 查找全部的keyWords列表，使用权重分析进行匹配
     */
    List<WeChatKeyWords> findKeyWordsAll();

    /**
     * 根据输入的内容，查询核实的记录
     */
    WeChatKeyWords findMatching(@Param(value = "content") String content);

    /**
     * 微信账号解绑
     */
    void unContactWechat(@Param(value = "openid") String openid);

    /**
     * 微信账号、平台账号绑定
     */
    void contactWechat(WechatUserBind userBind);

    /**
     * 通过OpenId 查询该用户是否绑定
     *
     * @param openId
     * @return WechatUserBind 或者null
     * null 表示未关注微信
     */
    WechatUserBind findBindByOpenId(@Param(value = "openId") String openId);

    /**
     * 通过user_id 查询绑定关系
     *
     * @param user_id 用户系统ID
     * @return
     */
    WechatUserBind findBindByUserId(@Param(value = "user_id") String user_id);

    WechatUserBind findBindByUrlToken(@Param(value = "token") String token);

    /**
     * 修改绑定休息
     *
     * @param userBind
     */
    void updateBind(WechatUserBind userBind);

    /**
     * 微信材料新增
     *
     * @param wmm
     */
    void createMediaMaterial(WeChatMediaMaterial wmm);

    /**
     * 删除本地素材-记录
     *
     * @param mt_id 素材本地系统中的数据库库ID
     */
    void deleteMediaMaterial(@Param(value = "mt_id") Long mt_id);

    /**
     * 重新上传素材后需要更新对应的media_id 和  上传时间
     * 保证三天内有效
     *
     * @param wmm
     */
    void updateMediaMaterial(WeChatMediaMaterial wmm);

    /**
     * 根据微信官方id 查找素材
     */
    WeChatMediaMaterial findMediaMaterialByMediaId(@Param(value = "mt_media_id") String mt_media_id);

    /**
     * 根据文件系统ID 进行媒体文件查找
     */
    WeChatMediaMaterial findMediaMaterialByMtId(@Param(value = "mt_id") String mt_id);

    /**
     * test
     */
    String findSpecItemVal(@Param(value = "key") String key);
}
