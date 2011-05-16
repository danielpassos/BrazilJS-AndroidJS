package br.com.braziljs;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BrazilJSAndroidJS extends Activity {

	private WebView webView;
	private TextView textView;
	private Handler handler = new Handler();
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(Boolean.TRUE);
        webView.loadUrl("file:///android_asset/index.html");
        
        webView.setWebChromeClient(new WebChromeClient() {
        		public boolean onJsAlert(WebView view, String url, String message,
        				JsResult result) {
        			Toast.makeText(BrazilJSAndroidJS.this, message, Toast.LENGTH_SHORT).show();
        			result.confirm();
        			return true;
        		}
        });
        
        webView.addJavascriptInterface(new JavaScriptBrigde(), "braziljsBg");
        
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				webView.loadUrl("javascript:sayHelloFromHTML()");
			}
		});
        
        textView = (TextView) findViewById(R.id.textview);
        
    }
	
	private class JavaScriptBrigde {
		public void sayHelloFromAndroid(final String msg) {
//			Toast.makeText(BrazilJSAndroidJS.this, "Chamado do Android", Toast.LENGTH_SHORT).show();
			handler.post(new Runnable() {
				public void run() {
					textView.setText(msg);
				}
			});
		}
	}
}