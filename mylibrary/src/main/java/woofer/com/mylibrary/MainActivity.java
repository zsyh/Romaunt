package woofer.com.mylibrary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private ListView listView=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.list);
        List<Map<String, Object>> list=getData();
        listView.setAdapter(new MyAdspter(MainActivity.this, list));
    }


    public List<Map<String, Object>> getData(){
        List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("image", R.drawable.img_my_choose);
            map.put("title", "这是一个标题"+i);
            map.put("info", "这是一个详细信息"+i);
            list.add(map);
        }
        return list;
    }
}
