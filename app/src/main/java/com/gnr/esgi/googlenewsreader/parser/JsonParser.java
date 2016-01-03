package com.gnr.esgi.googlenewsreader.parser;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.Object;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonParser
{
    private static final Logger logger = Logger.getLogger(JsonParser.class);

    private InputStream content;
    private Double code;
    private ArrayList<LinkedTreeMap<String, Object>> datas = new ArrayList<>();

    public JsonParser(InputStream content)
    {
        BasicConfigurator.configure();
        this.content = content;
        this.parse();
    }

    public ArrayList<LinkedTreeMap<String, Object>> getDatas()
    {
        return this.datas;
    }

    public Integer getCode()
    {
        return this.code.intValue();
    }

    private void parse()
    {
        try
        {
            //Read the server response and attempt to parse it as JSON
            Reader reader = new InputStreamReader(this.content);

            Gson gson = new Gson();

            Map<String, Object> map = new HashMap<String, Object>();
            map = (Map<String, Object>) gson.fromJson(reader, map.getClass());

            this.code = (Double) map.get("code");

            if(map.get("result") instanceof LinkedTreeMap)
            {
                this.datas.add((LinkedTreeMap<String, Object>) map.get("result"));
            }
            else
            {
                this.datas =  (ArrayList<LinkedTreeMap<String, Object>>) map.get("result");
            }
        }
        catch(Exception e)
        {
            logger.log(Level.ERROR, "Failed to parse JSON => " + e);
        }
    }
}

