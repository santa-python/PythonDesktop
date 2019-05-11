package com.winocencio.portalAlunoApp;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.webkit.JavascriptInterface;

import java.io.InputStream;

import winocencio.portalAlunoApp.R;


public class MainActivity extends AppCompatActivity {

    private static int REQUEST_CODE=1;
    private static String msgTelaInicial = "Este aplicativo é desenvolvido por alunos do curso de sistemas de informação da Unisanta. Caso queira contribuir com o aplicativo, com sugestões, criticas, relatos de falhas ou no desenvolvimento do projeto, entrar em contato pelo e-mail: willian.i.alves@hotmail.com";
    private WebView myWebView;
    private MainActivity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myWebView = (WebView) findViewById(R.id.webview);

        //myWebView.setWebViewClient(new WebViewClient());
        myWebView .getSettings().setJavaScriptEnabled(true);
        myWebView .getSettings().setDomStorageEnabled(true);

        myWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {

                if( ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(activity, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, REQUEST_CODE);
                }else {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Toast.makeText(getApplicationContext(), "Baixando Arquivo: " + URLUtil.guessFileName(url, contentDisposition, mimetype),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //injectJS(view,"jscript.js"); //Adicionar em um arquivo Externo dps //
                myWebView.evaluateJavascript("javascript:document.querySelector('div:nth-of-type(1) .view-switcher span').innerText = '"+ msgTelaInicial +"';",null);
                super.onPageFinished(view, url);

            }

        });

        myWebView.loadUrl("https://portalaluno.unisanta.br/Login");
    }

    private void injectJS(WebView view, String scriptFile) {
        try {
            view.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var script = document.createElement('script');" +
                    "script.type = 'text/javascript';" +
                    "script.innerHTML = window.atob('" + "document.getElementById('RA').value = '143562';" + "');" +
                    "parent.appendChild(script)" +
                    "})()");
            System.out.println("Passou");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
