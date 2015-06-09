package oz.basscode.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by blue on 2015/6/9.
 */
public class Person extends BmobObject
{

    private String name;

    private String sex;

    public Person()
    {
    }

    public Person(String name, String sex)
    {
        this.name = name;
        this.sex = sex;
    }

    public Person(String tableName, String name, String sex)
    {
        super(tableName);
        this.name = name;
        this.sex = sex;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }
}
