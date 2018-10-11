package com.example.asif.youtube;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout swipe;
    LinearLayout layout;
    WebView wb;
    ProgressBar progressBar;
    ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout= findViewById(R.id.linlayout);
        //progessbar
        progressBar = findViewById(R.id.progessbar);
        progressBar.setMax(100);
        im = findViewById(R.id.im);
        swipe =findViewById(R.id.swipe);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wb.reload();
            }
        });



        //web activity
        wb=findViewById(R.id.wb);
        wb.loadUrl("https://www.google.com/");
        wb.getSettings().getJavaScriptEnabled();
        wb.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                layout.setVisibility(View.VISIBLE);
                swipe.setRefreshing(false);

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                layout.setVisibility(View.GONE);
                swipe.setRefreshing(false);
                super.onPageFinished(view, url);
            }
        });
        wb.setWebChromeClient(new WebChromeClient()
                              {


                                  @Override
                                  public void onProgressChanged(WebView view, int newProgress) {
                                      progressBar.setProgress(newProgress);
                                      super.onProgressChanged(view, newProgress);
                                  }

                                  @Override
                                  public void onReceivedTitle(WebView view, String title) {
                                      getSupportActionBar().setTitle(title);

                                      super.onReceivedTitle(view, title);
                                  }

                                  @Override
                                  public void onReceivedIcon(WebView view, Bitmap icon) {
                                      im.setImageBitmap(icon);
                                      super.onReceivedIcon(view, icon);
                                  }
                              }


        );



    }
    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //on Item of Menu selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.back:
                onBackpressed();
                break;

            case R.id.forward:
                forward();

            case R.id.refresh:
                onRefresh();

        }
        return super.onOptionsItemSelected(item);
    }

    //refresh method
    public  void onRefresh(){
        wb.reload();
    }

    //backPressed
     public  void onBackpressed(){
        if(wb.canGoBack()){
            wb.goBack();}
        else{
                new AlertDialog.Builder(this)
                        .setMessage("Do you want to close")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();




        }

     }

     public  void forward(){
        if(wb.canGoForward()){
            wb.goBack();
        }
        else{
            Toast.makeText(this, "Can't go forward", Toast.LENGTH_SHORT).show();
        }
     }




}
