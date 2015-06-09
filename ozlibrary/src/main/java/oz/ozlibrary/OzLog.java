package oz.ozlibrary;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by blue on 2015/6/9.
 */
public class OzLog
{



    public static void log(Context context, String msg)
    {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }

    public static void log(Context context, String msg, OzLogType eType)
    {

        switch (eType)
        {
            case Long:

                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

                return;

            case Short:

                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                return;


        }



    }

}

