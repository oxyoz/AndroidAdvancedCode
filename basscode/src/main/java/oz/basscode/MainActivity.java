package oz.basscode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import oz.basscode.bean.Person;
import oz.ozlibrary.OzLog;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // 初始化FinalActivity
        FinalActivity.initInjectedView(this);
        // 初始化BmobSDK
        Bmob.initialize(this, "01d82d7edcc8f3cf46cf7a312c583877");
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this, "01d82d7edcc8f3cf46cf7a312c583877");

        refreshData();

    }

    @ViewInject(id = R.id.showinfo)
    TextView txtShowInfo;

    @ViewInject(id = R.id.name)
    EditText edtName;

    @ViewInject(id = R.id.sex)
    EditText edtSex;

    private void refreshData()
    {

        BmobQuery<Person> queryResult = new BmobQuery<Person>();

        queryResult.findObjects(this, new FindListener<Person>()
        {
            @Override
            public void onSuccess(List<Person> list)
            {

                String infos = "";

                for (Person person : list)
                {

                    infos += "昵称：" + person.getName() + "\t" + "性别：" + person.getSex() + "\n";
                }

                txtShowInfo.setText(infos);

            }

            @Override
            public void onError(int i, String s)
            {

                OzLog.log(getBaseContext(), s);

            }

        });

    }


    private void add()
    {

        final Person p = new Person(edtName.getText().toString(), edtSex.getText().toString());

        p.save(this, new SaveListener()
        {
            @Override
            public void onSuccess()
            {

                OzLog.log(getBaseContext(), "保存成功" + "id:" + p.getObjectId());

                refreshData();

            }


            @Override
            public void onFailure(int i, String s)
            {

                OzLog.log(getBaseContext(), s);

            }

        });


    }


    private void delete(String id)
    {

        final Person p = new Person();

        p.delete(this, id, new DeleteListener()
        {
            @Override
            public void onSuccess()
            {

                OzLog.log(getBaseContext(), "删除成功");

                refreshData();

            }

            @Override
            public void onFailure(int i, String s)
            {

                OzLog.log(getBaseContext(), s);

            }
        });

    }


    private void update(String id)
    {

        final Person p = new Person();

        p.setSex(edtSex.getText().toString());

        p.update(this, id, new UpdateListener()
        {

            @Override
            public void onFailure(int i, String s)
            {

                OzLog.log(getBaseContext(), s);

            }


            @Override
            public void onSuccess()
            {

                OzLog.log(getBaseContext(), "修改成功");

                refreshData();

            }

        });


    }


    private void find()
    {

        BmobQuery<Person> queryResult = new BmobQuery<Person>();
        //优先在缓存中查询，如果缓存中没有，再从网络查询
        queryResult.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        //添加查询的条件
        queryResult.addWhereEqualTo(edtName.getText().toString().equals("") ? "sex" : "name", edtName.getText().toString().equals("") ? edtSex.getText().toString() : edtName.getText().toString());
        //默认为返回10条数据
        queryResult.setLimit(5);

        queryResult.findObjects(this, new FindListener<Person>()
        {
            @Override
            public void onSuccess(List<Person> list)
            {

                String infos = "";

                for (Person person : list)
                {

                    infos += "昵称：" + person.getName() + "\t" + "性别：" + person.getSex() + "\n";
                }

                txtShowInfo.setText(infos);

            }

            @Override
            public void onError(int i, String s)
            {

                OzLog.log(getBaseContext(), s);

            }

        });

    }


    public void onAdd(View view)
    {

        add();

    }


    public void onDelete(View view)
    {

        BmobQuery<Person> queryResult = new BmobQuery<Person>();
        //优先在缓存中查询，如果缓存中没有，再从网络查询
        queryResult.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        //添加查询的条件
        queryResult.addWhereEqualTo("name", edtName.getText().toString());

        queryResult.findObjects(this, new FindListener<Person>()
        {
            @Override
            public void onSuccess(List<Person> list)
            {

                delete(list.get(0).getObjectId());

            }

            @Override
            public void onError(int i, String s)
            {



            }
        });

    }


    public void onUpdate(View view)
    {

        BmobQuery<Person> queryResult = new BmobQuery<Person>();
        //优先在缓存中查询，如果缓存中没有，再从网络查询
        queryResult.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        //添加查询的条件
        queryResult.addWhereEqualTo("name", edtName.getText().toString());

        queryResult.findObjects(this, new FindListener<Person>()
        {
            @Override
            public void onSuccess(List<Person> list)
            {

                update(list.get(0).getObjectId());

            }

            @Override
            public void onError(int i, String s)
            {


            }
        });


    }


    public void onFind(View view)
    {

        find();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();


        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
