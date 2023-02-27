package com.websarva.wings.android.screentransition;
package com.example.mailsend;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

public class Repassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncTask a=new asyncTask();
                a.execute("Gmailのアカウント名（@gmail.comの前まで）",
                        "Gmailのパスワード","テストタイトル","送信完了\n本文をここに記述する") ;
            }
        });
    }

    private class asyncTask extends android.os.AsyncTask{
        protected String account;
        protected String password;
        protected String title;
        protected String text;

        @Override
        protected Object doInBackground(Object... obj){
            account=(String)obj[0];
            password=(String)obj[1];
            title=(String)obj[2];
            text=(String)obj[3];

            java.util.Properties properties = new java.util.Properties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.socketFactory.post", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            final javax.mail.Message msg = new javax.mail.internet.MimeMessage(javax.mail.Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication(account,password);
                }
            }));

            try {
                msg.setFrom(new javax.mail.internet.InternetAddress(account + "@gmail.com"));
                //自分自身にメールを送信
                msg.setRecipients(javax.mail.Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(account + "@gmail.com"));
                msg.setSubject(title);
                msg.setText(text);

                javax.mail.Transport.send(msg);

            } catch (Exception e) {
                return (Object)e.toString();
            }

            return (Object)"送信が完了しました";

        }
        @Override
        protected void onPostExecute(Object obj) {
            //画面にメッセージを表示する
            Toast.makeText(MainActivity.this,(String)obj,Toast.LENGTH_LONG).show();
        }
    }
}