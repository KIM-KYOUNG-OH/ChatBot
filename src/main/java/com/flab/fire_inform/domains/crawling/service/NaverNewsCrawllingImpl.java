package com.flab.fire_inform.domains.crawling.service;
import com.flab.fire_inform.domains.crawling.dto.EconomyNewsUrl;
import com.flab.fire_inform.global.exception.CustomException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NaverNewsCrawllingImpl implements NewsCrawlling {
    /**
     * @return newsList
     * @throws IOException
     */
    @Override
    public List<String> getNaverNewsContents(String url) throws IOException {
        List<String> contentsList = new ArrayList<>();
        int size = getPgaing(url);

        for(int i =1 ; i<= size; i++) {
            String pageUrl = url + "&page=" + i;
            Document doc = Jsoup.connect(pageUrl).get();
            Elements contents;
            //헤드라인과 url 가져오기

            /*
            main과 domain 별로 분리
             */
            if (url.equals(EconomyNewsUrl.MAIN.getUrl())){
                contents = doc.select("tr > td.content > div.content > div.list_body > div._persist " +
                        "> div.cluster > div.cluster_group > div.cluster_head > div.cluster_head_inner > div.cluster_head_topic_wrap > h2.cluster_head_topic > a");
            }else{
                contents = doc.select("tr > td.content > div.content > div.list_body > ul > li > dl > dt:not(.photo) > a");
            }

            for (Element content : contents) {
                contentsList.add(content.toString());
            }
        }
        log.info("[NaverNesCrawllingImpl] :::::: contentList ={}" + contentsList.toString());
        return contentsList;
    }

    public List<String> getNaverNewsEconomyContents(String url) throws IOException {
        List<String> contentsList = new ArrayList<>();
        int size = getPgaing(url);

            Document doc = Jsoup.connect(url).get();

            //헤드라인과 url 가져오기
            Elements contents = doc.select("tr > td.content > div.content > div.list_body > ul > li > dl > dt:not(.photo) > a");
            for (Element content : contents) {
                contentsList.add(content.toString());
            }

        log.info("[NaverNesCrawllingImpl] :::::: contentList ={}" + contentsList.toString());
        return contentsList;
    }



    /**
     * 해당 url 페이지 체크
     * @return paging
     * @throws IOException
     */
    @Override
    public int getPgaing(String url) throws IOException{
        Document doc = Jsoup.connect(url).get();
        Elements contents = doc.select("tr > td.content > div.content > div.paging > a");

        // 자신의 페이지는 count 안된다.
        int paging = contents.size() + 1;

        log.info("[NaverNesCrawllingImpl] :::::: paging ={}" + paging);
        return paging;
    }

    /*
     * url 분기
     */
    @Override
    public String convertURL(String domain){
        if(domain.equals("MAIN")){
            return EconomyNewsUrl.MAIN.getUrl();
        }
        if(domain.equals("FINANCE")){
            return EconomyNewsUrl.FINANCE.getUrl();
        }
        if(domain.equals("STOCK")){
            return EconomyNewsUrl.STOCK.getUrl();
        }
        if(domain.equals("INDUSTRY")){
            return EconomyNewsUrl.INDUSTRY.getUrl();
        }
        if(domain.equals("VENTURE")){
            return EconomyNewsUrl.VENTURE.getUrl();
        }
        if(domain.equals("ESTATE")){
            return EconomyNewsUrl.ESTATE.getUrl();
        }
        if(domain.equals("GLOBAL")){
            return EconomyNewsUrl.GLOBAL.getUrl();
        }
        if(domain.equals("LIFE")){
            return EconomyNewsUrl.LIFE.getUrl();
        }
        if(domain.equals("ORDINARY")){
            return EconomyNewsUrl.ORDINARY.getUrl();
        }

        throw new CustomException(ErrorCode.DOMAIN_NOT_FOUND) ;
    }

    
    // 예약일자를 받았아서 사용할 수 있었으면 좋겠다.
    //cron = "* * * * * *"
    // *        *        *        *        *        *        *
    //초       분      시      일       월     요일    년도(생략가능)

}