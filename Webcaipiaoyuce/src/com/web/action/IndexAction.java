package com.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.action.BaseAction;
import com.system.bean.AllClassBean;
import com.system.pojo.Catalog;
import com.system.pojo.Contents;
import com.system.pojo.FileInfo;
import com.system.pojo.Links;
import com.system.pojo.OpenCatalog;
import com.system.pojo.OpenContents;
import com.system.pojo.OpinionPoll;
import com.system.pojo.Option;
import com.system.pojo.Survey;
import com.system.pojo.SurveyInfo;
import com.system.pojo.Titlepic;
import com.system.pojo.Video;
import com.system.pojo.ZlCatalog;
import com.system.pojo.ZlContents;
import com.system.services.OptionServices;
import com.system.utils.DateUtils;
import com.web.pojo.SearchVO;
import com.web.services.FrontCatalogServices;
import com.web.services.FrontContentsServices;
import com.web.services.FrontFileInfoServices;
import com.web.services.FrontLinksServices;
import com.web.services.FrontOpinionPollServices;
import com.web.services.FrontSearchServices;
import com.web.services.FrontOpenCatalogServices;
import com.web.services.FrontOpenContentsServices;
import com.web.services.FrontSurveyInfoServices;
import com.web.services.FrontSurveyServices;
import com.web.services.FrontTitlepicServices;
import com.web.services.FrontVideoServices;
import com.web.services.FrontZlCatalogServices;
import com.web.services.FrontZlContentsServices;
import com.web.utils.HtmlRegexpUtil;
import com.web.utils.PropertiesUtils;
import com.web.utils.StringUtils;

/**
 * @author hchch
 *         <p/>
 *         2014年8月15日
 */
public class IndexAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    @Autowired
    private FrontCatalogServices frontCatalogServices;
    @Autowired
    private FrontLinksServices frontLinksServices;
    @Autowired
    private FrontContentsServices frontContentsServices;
    @Autowired
    private FrontVideoServices frontVideoServices;

    @Autowired
    private FrontFileInfoServices frontFileInfoServices;
    @Autowired
    private FrontSearchServices frontSearchServices;

    @Resource(name = "frontOpenCatalogServices")
    private FrontOpenCatalogServices frontOpenCatalogServices;


    @Resource(name = "frontSurveyServices")
    private FrontSurveyServices frontSurveyServices;

    @Resource(name = "frontSurveyInfoServices")
    private FrontSurveyInfoServices frontSurveyInfoServices;

    @Resource(name = "frontZlCatalogServices")
    private FrontZlCatalogServices frontZlCatalogServices;

    @Resource(name = "frontOpenContentsServices")
    private FrontOpenContentsServices frontOpenContentsServices;

    @Resource(name = "frontZlContentsServices")
    private FrontZlContentsServices frontZlContentsServices;

    @Autowired
    private OptionServices optionServices;
    @Resource(name="frontOpinionPollServices")
	private FrontOpinionPollServices frontOpinionPollServices;

    @Autowired
    private FrontTitlepicServices frontTitlepicServices;

    public String index() {
        logger.info(">>>>>>>>>>IndexAction:index>>>>>>>>>>");

        String path = request.getSession().getServletContext().getRealPath("/");
        String propertiesPath = path + "WEB-INF/classes/web_index.properties";
        String value = "";

//        //网上调查
//        logger.info(">>>>>>>>>>IndexAction:index:网上调查>>>>>>>>>>");
//        Survey s = new Survey();
//        s.setStatus("1");
//        List<Survey> list = frontSurveyServices.front_queryAllSurveyList(s);
//        Iterator<Survey> it = list.iterator();
//        List<Survey> sl = new ArrayList<Survey>();
//        while (it.hasNext()) {
//            Survey survey = (Survey) it.next();
//            List<SurveyInfo> listInfo = frontSurveyInfoServices.front_querySurveyInfo(survey.getTheID().toString());
//            if (listInfo.size() < 1) {
//                sl.add(survey);
//            }
//        }
//        list.removeAll(sl);
//        request.setAttribute("list", list);

//        //民意征集
//        logger.info(">>>>>>>>>>IndexAction:index:民意征集>>>>>>>>>>");
//        OpinionPoll op = new OpinionPoll();
//		op.setStatus("1");
//		op.setNowDate(DateUtils.getServerDate());
//		List<OpinionPoll> contents45List = frontOpinionPollServices.queryOpinionPollListByBean(op);
//		request.setAttribute("contents45List", contents45List);


        //普宁头条新闻
        logger.info(">>>>>>>>>>IndexAction:index:普宁头条新闻>>>>>>>>>>");
        Contents contents10 = new Contents();
        value = PropertiesUtils.readValue(propertiesPath, "hlxwCatalogID");
        contents10.setCatalogID(Integer.valueOf(value));
        contents10.setStatus("1");
        contents10.setTopNum(null);
        contents10.setIsTop("1");
        contents10 = frontContentsServices.queryContentsByBean(contents10);
        
        //普宁新闻catalogID=10
        logger.info(">>>>>>>>>>IndexAction:index:普宁新闻>>>>>>>>>>");
        if (contents10 != null && contents10.getTheID() != null) {//如果有头条新闻，则查询7条新闻
        	contents10.setContents(HtmlRegexpUtil.Html2Text(contents10.getContents()));
        	request.setAttribute("contents10", contents10);
            contents10 = new Contents();
            contents10.setCatalogID(Integer.valueOf(value));
            contents10.setStatus("1");
            contents10.setTopNum("7");
            contents10.setIsTop("0");
            List<Contents> contents10List = frontContentsServices.queryTopContentsList(contents10);
            request.setAttribute("contents10List", contents10List);
        } else {//如果没有头条新闻，则查询8条新闻，第一条为头条新闻
            contents10 = new Contents();
            contents10.setCatalogID(Integer.valueOf(value));
            contents10.setStatus("1");
            contents10.setTopNum("10");
            contents10.setIsTop("0");
            List<Contents> contents10List = frontContentsServices.queryTopContentsList(contents10);
            request.setAttribute("contents10List", contents10List);
        }

        //新闻图片
        logger.info(">>>>>>>>>>IndexAction:index:新闻图片>>>>>>>>>>");
        AllClassBean picBean = new AllClassBean();
        value = PropertiesUtils.readValue(propertiesPath, "xwtpCatalogPID");
        picBean.setCatalogPID(value);
        picBean.setIsindex("1");
        picBean.setFileType("1");
        picBean.setTopNum("4");
        List<AllClassBean> picIndexList = frontFileInfoServices.queryTopPicIndex(picBean);
        request.setAttribute("picIndexList", picIndexList);

        //专题
        logger.info(">>>>>>>>>>IndexAction:index:专题>>>>>>>>>>");
        Catalog catalog = new Catalog();
        value = PropertiesUtils.readValue(propertiesPath, "ztCatalogPID");
        catalog.setCatalogType("t_specialTopic");
        catalog.setParentID(value);
//		catalog.setTopNum("5");
        List<Catalog> ztCatalogList = frontCatalogServices.queryCatalogListByBean(catalog);
//		List<Catalog> ztCatalogList = frontCatalogServices.queryTopCatalogList(catalog);
        request.setAttribute("ztCatalogList", ztCatalogList);

        //视频新闻
        logger.info(">>>>>>>>>>IndexAction:index:视频新闻>>>>>>>>>>");
        Video video = new Video();
        value = PropertiesUtils.readValue(propertiesPath, "spxwCatalogID");
        video.setCatalogID(Integer.valueOf(value));
        video.setStatus("1");
        video.setTopNum("10");
        List<Video> videoList = frontVideoServices.queryTopVideoList(video);
        request.setAttribute("videoList", videoList);

        //首页视频
//        logger.info(">>>>>>>>>>IndexAction:index:首页视频>>>>>>>>>>");
//        video = new Video();
//        video.setStatus("1");
//        video.setIsindex("1");
//        video.setTopNum("1");
//        List<Video> videoList2 = frontVideoServices.queryTopVideoList(video);
//        if (videoList2 != null && videoList2.size() > 0) {
//            request.setAttribute("videoIndex", videoList2.get(0));
//        }
        //首页视频是否自动播放
//        logger.info(">>>>>>>>>>IndexAction:index:首页视频是否自动播放>>>>>>>>>>");
//        Option option = new Option();
//        option.setItem("videoAutoPlay");
//        option = optionServices.queryOptionByBean(option);
//        boolean autostart = false;
//        if (option.getCode().equals("1")) {
//            autostart = true;
//        }
//        request.setAttribute("autostart", autostart);

        //首页对联是否显示
        logger.info(">>>>>>>>>>IndexAction:index:首页对联是否显示>>>>>>>>>>");
        Option option = new Option();
        option.setItem("dlPicShow");
        option = optionServices.queryOptionByBean(option);
        request.setAttribute("isDlShow", option.getCode());

        //便民服务
//        logger.info(">>>>>>>>>>IndexAction:index:便民服务>>>>>>>>>>");
//        Links sLinks = new Links();
//        sLinks.setLinkKind("2");
//        sLinks.setTopNum("12");
//        sLinks.setStatus("1");
//        List<Links> sLinksList = frontLinksServices.queryTopLinksList(sLinks);
//        request.setAttribute("sLinksList", sLinksList);

        //图片友情连接
        logger.info(">>>>>>>>>>IndexAction:index:图片友情连接>>>>>>>>>>");
        Links picLinks = new Links();
        picLinks.setLinkKind("1");
        picLinks.setLink_type("1");
        picLinks.setStatus("1");
        picLinks.setTopNum("5");
        List<Links> picLinksList = frontLinksServices.queryTopLinksList(picLinks);
        request.setAttribute("picLinksList", picLinksList);

        //所属框友情连接
        logger.info(">>>>>>>>>>IndexAction:index:所属框友情连接>>>>>>>>>>");
        Links links = new Links();
        links.setLinkKind("1");
        links.setLink_type("2");
        links.setStatus("1");
        List<Links> selectLinksList = frontLinksServices.queryLinksListByBean(links);
        request.getSession().setAttribute("selectLinksList", selectLinksList);
        
	    String sysPropertiesPath = path + "WEB-INF/classes/system.properties";
	    String sysValue = "";
	    sysValue = PropertiesUtils.readValue(sysPropertiesPath, "linksKeyNum");
		String key = "";
		Map<Integer,String> map = new HashMap<Integer,String>();
		if(Integer.valueOf(sysValue).intValue() > 0){
			int linksKeyNum = Integer.valueOf(sysValue);
			for(int i = 1;i <= linksKeyNum;i++){
				key = "linksKey"+i;
				sysValue = PropertiesUtils.readValue(sysPropertiesPath, key);
				map.put(i, sysValue);
			}
			request.setAttribute("linksKeyNum", linksKeyNum);
			request.setAttribute("linksMap", map);
		}

        //信息公开栏目
//        logger.info(">>>>>>>>>>IndexAction:index:信息公开栏目>>>>>>>>>>");
//        OpenCatalog openCatalog = new OpenCatalog();
//        value = PropertiesUtils.readValue(propertiesPath, "xxgkCatalogPID");
//        openCatalog.setParentID(value);
//        openCatalog.setStatus("1");
//        List<OpenCatalog> openTop8 = frontOpenCatalogServices.queryTopOpenCatalogList(openCatalog);
//        request.setAttribute("openTop8", openTop8);

        //重点领域栏目
        logger.info(">>>>>>>>>>IndexAction:index:重点领域栏目>>>>>>>>>>");
        ZlCatalog zlcatalog = new ZlCatalog();
        value = PropertiesUtils.readValue(propertiesPath, "zdlyCatalogPID");
        zlcatalog.setParentID(value);
        zlcatalog.setStatus("1");
        List<ZlCatalog> zlTop8 = frontZlCatalogServices.queryTopZlCatalogList(zlcatalog);
        request.setAttribute("zlTop8", zlTop8);

        //信息公开_政府文件
//        logger.info(">>>>>>>>>>IndexAction:index:信息公开_政府文件>>>>>>>>>>");
//        OpenContents openContents = new OpenContents();
//        openContents.setTopNum("10");
//        value = PropertiesUtils.readValue(propertiesPath, "zfwjCatalogID");
//        openContents.setCatalogID(value);
//        List<OpenContents> openTop8Contents = frontOpenContentsServices.queryTopOpenContentsList(openContents);
//        request.setAttribute("openTop8Contents", openTop8Contents);
        //重点领域_政府公报
        logger.info(">>>>>>>>>>IndexAction:index:重点领域_政府公报>>>>>>>>>>");
        ZlContents zlContents = new ZlContents();
        zlContents.setTopNum("8");
        value = PropertiesUtils.readValue(propertiesPath, "zfwjCatalogID");
        zlContents.setCatalogID(value);
        List<ZlContents> zfgbContents = frontZlContentsServices.queryTopZlContentsList(zlContents);
        request.setAttribute("zfgbContents", zfgbContents);

        //重点领域_人事任免
        logger.info(">>>>>>>>>>IndexAction:index:重点领域_人事任免>>>>>>>>>>");
        zlContents = new ZlContents();
        zlContents.setTopNum("8");
        value = PropertiesUtils.readValue(propertiesPath, "rsrmCatalogID");
        zlContents.setCatalogID(value);
        List<ZlContents> zlTop8Contents = frontZlContentsServices.queryTopZlContentsList(zlContents);
        request.setAttribute("zlTop8Contents", zlTop8Contents);
        
        //重点领域_人事任免
        logger.info(">>>>>>>>>>IndexAction:index:重点领域_重大项目>>>>>>>>>>");
        zlContents = new ZlContents();
        zlContents.setTopNum("8");
        value = PropertiesUtils.readValue(propertiesPath, "zdxmCatalogID");
        zlContents.setCatalogID(value);
        List<ZlContents> zdxmContents = frontZlContentsServices.queryTopZlContentsList(zlContents);
        request.setAttribute("zdxmContents", zdxmContents);


        Contents contents43 = new Contents();
        value = PropertiesUtils.readValue(propertiesPath, "gnwxwCatalogID");
        //国内外头条新闻
        logger.info(">>>>>>>>>>IndexAction:index:国内外头条新闻>>>>>>>>>>");
        contents43.setCatalogID(Integer.valueOf(value));
        contents43.setStatus("1");
        contents43.setTopNum(null);
        contents43.setIsTop("1");
        contents43 = frontContentsServices.queryContentsByBean(contents43);
        
        //国内外新闻
        logger.info(">>>>>>>>>>IndexAction:index:国内外新闻>>>>>>>>>>");
        if (contents43 != null && contents43.getTheID() != null) {//如果有头条新闻，则查询7条新闻
        	contents43.setContents(HtmlRegexpUtil.Html2Text(contents43.getContents()));
        	request.setAttribute("contents43", contents43);
            contents43 = new Contents();
            contents43.setCatalogID(Integer.valueOf(value));
            contents43.setStatus("1");
            contents43.setTopNum("7");
            contents43.setIsTop("0");
            List<Contents> contents43List = frontContentsServices.queryTopContentsList(contents43);
            request.setAttribute("contents43List", contents43List);
        } else {//如果没有头条新闻，则查询8条新闻，第一条为头条新闻
            contents43 = new Contents();
            contents43.setCatalogID(Integer.valueOf(value));
            contents43.setStatus("1");
            contents43.setTopNum("10");
            contents43.setIsTop("0");
            List<Contents> contents43List = frontContentsServices.queryTopContentsList(contents43);
            request.setAttribute("contents43List", contents43List);
        }

        //通知公告
        logger.info(">>>>>>>>>>IndexAction:index:通知公告>>>>>>>>>>");
        Contents contents68 = new Contents();
        value = PropertiesUtils.readValue(propertiesPath, "tzggCatalogID");
        contents68.setCatalogID(Integer.valueOf(value));
        contents68.setStatus("1");
        contents68.setTopNum("8");
        List<Contents> content68List = frontContentsServices.queryTopContentsList(contents68);
        request.setAttribute("contents68List", content68List);

        //投资服务
        logger.info(">>>>>>>>>>IndexAction:index:投资服务>>>>>>>>>>");
        Contents contents65 = new Contents();
        value = PropertiesUtils.readValue(propertiesPath, "tzfwCatalogPID");
//		contents65.setCatalogID(65);
        contents65.setCatalogPid(value);
        contents65.setStatus("1");
        contents65.setTopNum("7");
//		List<Contents> contents65List = frontContentsServices.queryTopContentsList(contents65);
        List<Contents> contents65List = frontContentsServices.queryContentsByCataPid(contents65);
        request.setAttribute("contents65List", contents65List);

        //旅游天地
        logger.info(">>>>>>>>>>IndexAction:index:旅游天地>>>>>>>>>>");
        value = PropertiesUtils.readValue(propertiesPath, "lvtdCatalogPID");
//		contents65.setCatalogID(51);
        contents65 = new Contents();
        contents65.setCatalogPid(value);
        contents65.setStatus("1");
        contents65.setTopNum("7");
//		List<Contents> contents51List = frontContentsServices.queryTopContentsList(contents65);
        List<Contents> contents51List = frontContentsServices.queryContentsByCataPid(contents65);
        request.setAttribute("contents51List", contents51List);
        //经济文化
        logger.info(">>>>>>>>>>IndexAction:index:经济文化>>>>>>>>>>");
        value = PropertiesUtils.readValue(propertiesPath, "jjwhCatalogPID");
//		contents65.setCatalogID(51);
        Contents contents7 = new Contents();
        contents7.setCatalogID(Integer.valueOf(value));
//		List<Contents> contents51List = frontContentsServices.queryTopContentsList(contents65);
        List<Contents> contents7List = frontContentsServices.queryContentsListByBean(contents7);
        request.setAttribute("contents7List", contents7List);

        //今日关注
        logger.info(">>>>>>>>>>IndexAction:index:今日关注>>>>>>>>>>");
        Integer[] ids = {10, 43};
        option = new Option();
        option.setItem("focusNewsNums");
        option = optionServices.queryOptionByBean(option);
        if(option != null && option.getCode() != null && Integer.valueOf(option.getCode())>0){
        	 Contents jrgzTemp = new Contents();
             jrgzTemp.setIds(ids);
             jrgzTemp.setIsFocus("1");
        	
        	List<Contents> focusList = frontContentsServices.queryContentsByIds(jrgzTemp);
        	request.setAttribute("focusList", focusList);
        }
        

//        //市民服务
//        logger.info(">>>>>>>>>>IndexAction:index:市民服务>>>>>>>>>>");
//        Catalog catalog1 = new Catalog();
//        value = PropertiesUtils.readValue(propertiesPath, "smfwCatalogPID");
//        catalog1.setParentID(value);
//        catalog1.setStatus("1");
//        List<Catalog> catalog98List = frontCatalogServices.queryCatalogListByBean(catalog1);
//        request.setAttribute("catalog98List", catalog98List);
//
//        //企业服务
//        logger.info(">>>>>>>>>>IndexAction:index:企业服务>>>>>>>>>>");
//        Catalog catalog2 = new Catalog();
//        value = PropertiesUtils.readValue(propertiesPath, "qyfwCatalogPID");
//        catalog2.setParentID(value);
//        catalog2.setStatus("1");
//        List<Catalog> catalog99List = frontCatalogServices.queryCatalogListByBean(catalog2);
//        request.setAttribute("catalog99List", catalog99List);

        //政府信息
//        logger.info(">>>>>>>>>>IndexAction:index:政府信息>>>>>>>>>>");
//        Contents contents130 = new Contents();
//        value = PropertiesUtils.readValue(propertiesPath, "zfxxCatalogID");
//        contents130.setCatalogID(Integer.valueOf(value));
//        contents130.setStatus("1");
//        contents130.setTopNum("15");
//        List<Contents> contents130List = frontContentsServices.queryTopContentsList(contents130);
//        request.setAttribute("contents130List", contents130List);
        
        //重点领域信息公开专栏
        logger.info(">>>>>>>>>>IndexAction:index:重点领域信息公开专栏>>>>>>>>>>");
		OpenCatalog openCatalog = new OpenCatalog();
		value = PropertiesUtils.readValue(propertiesPath, "zdlyxxkgzlCatalogPID");
		openCatalog.setParentID(value);
		openCatalog.setStatus("1");
		openCatalog.setTopNum("14");
		List<OpenCatalog> openTop8 = frontOpenCatalogServices.queryTopOpenCatalogList(openCatalog);
		request.setAttribute("openTop8", openTop8);
        
		logger.info(">>>>>>>>>>IndexAction:index:应急管理>>>>>>>>>>");
		ZlContents yjgl = new ZlContents();
		value = PropertiesUtils.readValue(propertiesPath, "yjglCatalogID");
		yjgl.setCatalogID(value);
		yjgl.setTopNum("7");
		yjgl.setStatus("1");
		List<ZlContents> yjglList = frontZlContentsServices.queryContentsByIds(yjgl);
		System.out.println(">>>>>>>>>>>>>>>>>>>"+yjglList.size());
		request.setAttribute("yjglList", yjglList);
		
        return "index";
    }

    /**
     * 全文搜索（文章、视频）
     *
     * @return
     */
    public String searchList() {
        String title = request.getParameter("title");
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>beforeTitle:"+title+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        SearchVO s = new SearchVO();
        title = StringUtils.StringFilter(title);
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>filterTitle:"+title+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        s.setTitle(title);
        List<SearchVO> resultList = frontSearchServices.querySearchVOByBean(s);
        request.setAttribute("resultList", resultList);
        request.setAttribute("title", title);
        return "searchList";
    }

    public String searchShow() {
        String id = request.getParameter("id");
        String tableName = request.getParameter("tableName");
        if (tableName != null && id != null) {
            if (tableName.equals("t_video")) {//视频
                Video video = new Video();
                video.setTheID(Integer.valueOf(id));
                video = frontVideoServices.queryVideoByBean(video);
                request.setAttribute("video", video);
                request.setAttribute("videoUrl", video.getUrl());
                return "searchVideoShow";
            } else if (tableName.equals("t_contents")) {//普通文章
                Contents contents = new Contents();
                contents.setTheID(Integer.valueOf(id));
                contents = frontContentsServices.queryContentsByBean(contents);
                request.setAttribute("contents", contents);
                //查找图片
                FileInfo fileInfo = new FileInfo();
                fileInfo.setContentID(contents.getTheID());
                fileInfo.setIsshow("1");
                fileInfo.setFileType("1");
                List<FileInfo> picList = frontFileInfoServices.front_fileInfoList(fileInfo);
                request.setAttribute("picList", picList);

                //查找附件
                fileInfo = new FileInfo();
                fileInfo.setContentID(contents.getTheID());
                fileInfo.setFileType("2");
                fileInfo.setIsshow("1");
                List<FileInfo> fileList = frontFileInfoServices.front_fileInfoList(fileInfo);
                request.setAttribute("fileList", fileList);

                request.setAttribute("tableName", tableName);
            } else if (tableName.equals("zl_contents")) {//重点领域
                ZlContents contents = frontZlContentsServices.queryZlContentsByBean(Integer.valueOf(id));
                request.setAttribute("contents", contents);

                request.setAttribute("tableName", tableName);
            } else if (tableName.equals("open_contents")) {//政务公开
                OpenContents contents = frontOpenContentsServices.frontOpenContentsDetail(Integer.valueOf(id));
                request.setAttribute("contents", contents);

                request.setAttribute("tableName", tableName);
            }
        }
        return "searchContentsShow";
    }

    /**
     * 便民服务列表
     *
     * @return
     */
    public String servicesList() {
        Links links = new Links();
        links.setLinkKind("2");
        links.setStatus("1");
        List<Links> servicesList = frontLinksServices.queryLinksListByBean(links);
        request.setAttribute("servicesList", servicesList);
        return "servicesList";
    }

    /**
     * 同个titlepic的id查到对应的url
     */
    public void queryUrlById() {
        PrintWriter pw = null;
        String theID = request.getParameter("id");
        String result = "";
        try {
            this.getResponse().setCharacterEncoding("UTF-8");
            pw = this.getResponse().getWriter();
            if (theID != null && !"".equals(theID)) {
                Titlepic titlepic = new Titlepic();
                titlepic.setTheID(Integer.valueOf(theID));
                titlepic = frontTitlepicServices.queryTitlepicByBean(titlepic);
                if (titlepic.getLinkUrl() != null) {
                    result = titlepic.getLinkUrl();
                }
            }
            pw.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public FrontCatalogServices getFrontCatalogServices() {
        return frontCatalogServices;
    }

    public void setFrontCatalogServices(FrontCatalogServices frontCatalogServices) {
        this.frontCatalogServices = frontCatalogServices;
    }

    public FrontLinksServices getFrontLinksServices() {
        return frontLinksServices;
    }

    public void setFrontLinksServices(FrontLinksServices frontLinksServices) {
        this.frontLinksServices = frontLinksServices;
    }

    public FrontContentsServices getFrontContentsServices() {
        return frontContentsServices;
    }

    public void setFrontContentsServices(FrontContentsServices frontContentsServices) {
        this.frontContentsServices = frontContentsServices;
    }

    public FrontVideoServices getFrontVideoServices() {
        return frontVideoServices;
    }

    public void setFrontVideoServices(FrontVideoServices frontVideoServices) {
        this.frontVideoServices = frontVideoServices;
    }

    public FrontFileInfoServices getFrontFileInfoServices() {
        return frontFileInfoServices;
    }

    public void setFrontFileInfoServices(FrontFileInfoServices frontFileInfoServices) {
        this.frontFileInfoServices = frontFileInfoServices;
    }

    public FrontSearchServices getFrontSearchServices() {
        return frontSearchServices;
    }

    public void setFrontSearchServices(FrontSearchServices frontSearchServices) {
        this.frontSearchServices = frontSearchServices;
    }

    public OptionServices getOptionServices() {
        return optionServices;
    }

    public void setOptionServices(OptionServices optionServices) {
        this.optionServices = optionServices;
    }

    public FrontTitlepicServices getFrontTitlepicServices() {
        return frontTitlepicServices;
    }

    public void setFrontTitlepicServices(FrontTitlepicServices frontTitlepicServices) {
        this.frontTitlepicServices = frontTitlepicServices;
    }

}
