package com.venndor.venndor.ui.Traning;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
import com.venndor.venndor.R;
import com.venndor.venndor.view.AppUrls;
import com.venndor.venndor.view.AppUtils;
import com.venndor.venndor.view.BaseFragment;
import com.venndor.venndor.view.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityTraining extends BaseFragment {
    RecyclerView recyclerView, rvSubCatview;
    LinearLayoutManager linearLayoutManager, linearsLayoutManager;
    OfferAdapter offerAdapter;
    View rootView;
    RelativeLayout Rl_norecord;
    LinearLayout lldetails;
    Preferences pref;
    String selectedSubcategoryId="";

    RelativeLayout Rl_noLeads, Rl_leads;

    ArrayList<HashMap<String, String>> osubcatList = new ArrayList();
    ArrayList<HashMap<String, String>> questionlist = new ArrayList();
    ArrayList<HashMap<String, String>> Videolist = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.training_activity, container, false);
        findViewById();
        return rootView;
    }

    private void findViewById() {
        pref = new Preferences(mActivity);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        rvSubCatview = rootView.findViewById(R.id.rvSubCatview);
        Rl_norecord = rootView.findViewById(R.id.Rl_norecord);
        lldetails = rootView.findViewById(R.id.lldetails);
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearsLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        rvSubCatview.setLayoutManager(linearsLayoutManager);
        Rl_noLeads = rootView.findViewById(R.id.Rl_noLeads);
        Rl_leads = rootView.findViewById(R.id.Rl_leads);
        getOffers();
    }

    public void getOffers() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String token = pref.get("token");
        Log.v("ddurl", token);
        String url = AppUrls.getvendorsubcats;
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parsemyTicketsJsonResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }

    private void parsemyTicketsJsonResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                //Toast.makeText(mActivity, response.getString("message"), Toast.LENGTH_SHORT).show();
                osubcatList.clear();
                JSONObject jsonObjectt = response.getJSONObject("data");


                JSONArray jsonArray = jsonObjectt.getJSONArray("sub_categories");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject block_object = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    if(i==0) {
                        selectedSubcategoryId = block_object.get("id").toString();
                    }
                    map.put("id", block_object.get("id").toString());
                    map.put("sub_category_name", block_object.get("sub_category_name").toString());
                    osubcatList.add(map);
                }

                offerAdapter = new OfferAdapter(osubcatList);
                recyclerView.setAdapter(offerAdapter);
                GetTraining();

            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    public void GetTraining() {
        AppUtils.hideSoftKeyboard(mActivity);
        AppUtils.showRequestDialog(mActivity);
        String url = AppUrls.getvendortraning + selectedSubcategoryId;
        String token = pref.get("token");
        Log.v("ddurl", token);
        Log.v("data_url", url);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .addHeaders("Authorization", "Bearer " + token)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        AppUtils.hideDialog();
                        parseResponse(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        AppUtils.hideDialog();
                        Log.v("data", anError.getErrorBody());
                        Log.v("data", anError.getErrorDetail());
                    }
                });
    }

    private void parseResponse(JSONObject response) {
        try {
            AppUtils.hideDialog();
            questionlist.clear();
            Log.v("responseApiCheck", "empty " + response);
            if (response.getString("status").equals("true")) {
                String message = response.getString("message");
                //  Toast.makeText(mActivity, "" + message, Toast.LENGTH_SHORT).show();

                JSONObject dataobject = response.getJSONObject("data");
                JSONArray histories = dataobject.getJSONArray("trainings");
                for (int i = 0; i < histories.length(); i++) {
                    JSONObject loansObj = histories.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("id", loansObj.get("id").toString());
                    map.put("state_id", loansObj.get("state_id").toString());
                    map.put("city_id", loansObj.get("city_id").toString());
                    map.put("category_id", loansObj.get("category_id").toString());
                    map.put("sub_category_id", loansObj.get("sub_category_id").toString());
                    map.put("heading", loansObj.get("heading").toString());
                    map.put("title", loansObj.get("title").toString());
                    map.put("short_description", loansObj.get("short_description").toString());
                    map.put("full_description", loansObj.get("full_description").toString());
                    map.put("status", loansObj.get("status").toString());
                    map.put("created_at", loansObj.get("created_at").toString());
                    map.put("updated_at", loansObj.get("updated_at").toString());
                    map.put("get_related_videos", loansObj.get("get_related_videos").toString());

                    questionlist.add(map);
                }
                if (questionlist.isEmpty()) {
                    Rl_noLeads.setVisibility(View.VISIBLE);
                    Rl_leads.setVisibility(View.GONE);
                } else {
                    Rl_noLeads.setVisibility(View.GONE);
                    Rl_leads.setVisibility(View.VISIBLE);
                }
                QuesAdapter quesAdapter = new QuesAdapter(questionlist);
                rvSubCatview.setAdapter(quesAdapter);
            } else {
            }
        } catch (Exception e) {
            Log.v("error", String.valueOf(e));
        }
    }

    public class OfferAdapter extends RecyclerView.Adapter<OfferHolder> {

        ArrayList<HashMap<String, String>> data = new ArrayList();

        public OfferAdapter(ArrayList<HashMap<String, String>> bookingsList) {
            this.data = bookingsList;
        }

        @Override
        public OfferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_category, parent, false);
            return new OfferHolder(itemView);
        }

        public void onBindViewHolder(OfferHolder holder, final int position) {
            holder.categories.setText(data.get(position).get("sub_category_name"));
           if(data.get(position).get("id").equalsIgnoreCase(selectedSubcategoryId)){
               holder.categories.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));

           }else{
               holder.categories.setBackground(getResources().getDrawable(R.drawable.white_border));
           }
            holder.categories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Videolist.clear();
                  selectedSubcategoryId= data.get(position).get("id");
                    GetTraining();
                   notifyDataSetChanged();
                }
            });

           /* if (ischeck == true) {

                holder.categories.setBackground(getResources().getDrawable(R.drawable.blue_bottomview));
            }else{
                holder.categories.setBackground(getResources().getDrawable(R.drawable.white_border));
            }*/
           /* */
           /* holder.tvtab.setText(data.get(position).get("name"));
            Log.v("jgh",data.get(position).get("name"));
            holder.relativelayout.setVisibility(View.GONE);

            holder.categories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.relativelayout.setVisibility(View.VISIBLE);
                    querylist.clear();
                    try {

                        JSONArray questions = new JSONArray(data.get(position).get("questions"));
                        for (int i = 0; i < questions.length(); i++) {
                            JSONObject loansObj = questions.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("id", loansObj.get("id").toString());
                            map.put("user_type", loansObj.get("user_type").toString());
                            map.put("models", loansObj.get("models").toString());
                            map.put("queries", loansObj.get("queries").toString());
                            map.put("status", loansObj.get("status").toString());
                            map.put("created_at", loansObj.get("created_at").toString());
                            map.put("updated_at", loansObj.get("updated_at").toString());
                            querylist.add(map);

                        }
                       QuesAdapter quesAdapter = new QuesAdapter(querylist);
                        holder.rvQuestion.setAdapter(quesAdapter);
                        if(querylist.isEmpty()){
                            pref.set("models","other").commit();
                            ((Container) mActivity).displayView(16);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });*/

        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class OfferHolder extends RecyclerView.ViewHolder {

        LinearLayout llorder;
        TextView categories;
        RecyclerView rvQuestion;
        RelativeLayout relativelayout;

        public OfferHolder(View itemView) {
            super(itemView);
            /*  llorder = itemView.findViewById(R.id.llorder);*/
            categories = itemView.findViewById(R.id.categories);
            relativelayout = itemView.findViewById(R.id.relativelayout);
            rvQuestion = itemView.findViewById(R.id.rvQuestion);
            LinearLayoutManager layoutQManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
            rvQuestion.setLayoutManager(layoutQManager);

        }
    }

    public class QuesAdapter extends RecyclerView.Adapter<CommissionHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();
        boolean counter = false;

        public QuesAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public CommissionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_subcategory_collapse, parent, false);
            return new CommissionHolder(itemView);
        }

        public void onBindViewHolder(CommissionHolder holder, final int position) {
            holder.tvquestion.setText(data.get(position).get("heading"));
            holder.tvtitle.setText(data.get(position).get("title"));
            String myHtml = data.get(position).get("short_description");
            holder.tvvdoCollapse.setHtml(myHtml,
                    new HtmlHttpImageGetter(holder.tvvdoCollapse));
            String full_description = data.get(position).get("full_description");
            holder.tvlongdesc.setHtml(full_description,
                    new HtmlHttpImageGetter(holder.tvlongdesc));
            if (position == 0) {
                /*holder.itemView.setVisibility(View.VISIBLE);*/
                holder.rvVideo.setVisibility(View.VISIBLE);
                holder.rrquestion.setVisibility(View.VISIBLE);
                holder.llvdocollapse.setVisibility(View.VISIBLE);
            } else {
                /*holder.itemView.setVisibility(View.GONE);*/
                holder.rvVideo.setVisibility(View.GONE);
                holder.llvdocollapse.setVisibility(View.GONE);
                holder.rrquestion.setVisibility(View.VISIBLE);

            }




            if (data.get(position).get("get_related_videos") == null || data.get(position).get("get_related_videos").equalsIgnoreCase("")) {

                //holder.rvVideo.setVisibility(View.GONE);

            } else {
                try {

                    JSONArray orderInfo = new JSONArray(data.get(position).get("get_related_videos"));
                    for (int i = 0; i < orderInfo.length(); i++) {
                        JSONObject loansObj = orderInfo.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", loansObj.get("id").toString());
                        map.put("training_id", loansObj.get("training_id").toString());
                        map.put("video_code", loansObj.get("video_code").toString());
                        map.put("created_at", loansObj.get("created_at").toString());
                        map.put("updated_at", loansObj.get("updated_at").toString());
                        Videolist.add(map);
                    }
                   // holder.rvVideo.setVisibility(View.VISIBLE);
                    VideoAdapter videoAdapter = new VideoAdapter(Videolist);
                    holder.rvVideo.setAdapter(videoAdapter);
                    videoAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            holder.tvquestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (counter == false) {
                        counter = true;
                      /*  if (data.get(position).get("get_related_videos") == null || data.get(position).get("get_related_videos").equalsIgnoreCase("")) {

                            holder.rvVideo.setVisibility(View.GONE);

                        } else {
                            holder.rvVideo.setVisibility(View.VISIBLE);
                        }*/
                        holder.llvdocollapse.setVisibility(View.VISIBLE);
                        holder.rrquestion.setVisibility(View.VISIBLE);
                    } else {
                        counter = false;

                       /* if (data.get(position).get("get_related_videos") == null || data.get(position).get("get_related_videos").equalsIgnoreCase("")) {

                            holder.rvVideo.setVisibility(View.GONE);

                        } else {
                            holder.rvVideo.setVisibility(View.GONE);
                        }*/
                        holder.llvdocollapse.setVisibility(View.GONE);
                        holder.rrquestion.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class CommissionHolder extends RecyclerView.ViewHolder {
        TextView tvquestion, tvtitle;
        HtmlTextView tvvdoCollapse, tvlongdesc;
        RecyclerView rvVideo;
        RelativeLayout rrquestion;
        LinearLayout llvdocollapse;

        public CommissionHolder(View itemView) {
            super(itemView);
            tvquestion = itemView.findViewById(R.id.tvquestion);
            tvtitle = itemView.findViewById(R.id.tvtitle);
            tvvdoCollapse = itemView.findViewById(R.id.tvvdoCollapse);
            rrquestion = itemView.findViewById(R.id.rrquestion);
            rvVideo = itemView.findViewById(R.id.rvVideo);
            LinearLayoutManager layoutQManager = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
            rvVideo.setLayoutManager(layoutQManager);
            llvdocollapse = itemView.findViewById(R.id.llvdocollapse);
            tvlongdesc = itemView.findViewById(R.id.tvlongdesc);

        }
    }

    public class VideoAdapter extends RecyclerView.Adapter<chatHolder> {
        ArrayList<HashMap<String, String>> data = new ArrayList();
        YouTubePlayer Player;

        public VideoAdapter(ArrayList<HashMap<String, String>> favList) {
            data = favList;
        }

        @Override
        public chatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_video, parent, false);
            return new chatHolder(itemView);
        }

        public void onBindViewHolder(chatHolder holder, final int position) {


         /*   holder.ytPlayer.initialize(
                    api_key,
                    new YouTubePlayer.OnInitializedListener() {
                        // Implement two methods by clicking on red
                        // error bulb inside onInitializationSuccess
                        // method add the video link or the playlist
                        // link that you want to play In here we
                        // also handle the play and pause
                        // functionality
                        @Override
                        public void onInitializationSuccess(
                                YouTubePlayer.Provider provider,
                                YouTubePlayer youTubePlayer, boolean b)
                        {
                            youTubePlayer.loadVideo("TssC_i8_0rk");
                            youTubePlayer.play();
                        }

                        // Inside onInitializationFailure
                        // implement the failure functionality
                        // Here we will show toast
                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult
                                                                    youTubeInitializationResult)
                        {
                            Toast.makeText(mActivity, "Video player Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
*/

            //  holder.videoView.setVisibility(View.GONE);
           /* MediaController mediaController= new MediaController(mActivity);
            mediaController.setAnchorView(holder.videoView);

            //specify the location of media file
            Uri uri=Uri.parse("https://www.youtube.com/watch?v=ShlOUDbodgI");

            //Setting MediaController and URI, then starting the videoView
            holder.videoView.setMediaController(mediaController);
            holder.videoView.setVideoURI(uri);
            holder.videoView.requestFocus();
            holder.videoView.start();*/
            //  data.get(position).get("video_code");




        /* String frameVideo = "<html><body><br><iframe width=\"320\" height=\"200\" src=\"https://www.youtube.com/embed/XDYbEuY8nIc\" frameborder=\"0\" allowfullscreen></iframe></body></html>";


            holder.webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }
            });
            WebSettings webSettings =   holder.webview.getSettings();
            webSettings.setJavaScriptEnabled(true);
            holder.webview.loadData(frameVideo, "text/html", "utf-8");*/
            // holder.webview.loadData(""+data.get(position).get("video_code"), "text/html", "utf-8");
           /* holder.ivPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                            *//*    myViewHolder.youtube_web_view.setWebChromeClient(new WebChromeClient() {
                                });

                                WebSettings webSettings = myViewHolder.youtube_web_view.getSettings();
                                webSettings.setJavaScriptEnabled(true);
                                webSettings.setLoadWithOverviewMode(true);
                                webSettings.setUseWideViewPort(true);

                    myViewHolder.youtube_web_view.loadUrl("https://www.youtube.com/embed/" + arrayList1.get(i).get("videoId"));

                    myViewHolder.ivPlay.setVisibility(View.GONE);
                    myViewHolder.ivThumbnail.setVisibility(View.GONE);
                    myViewHolder.youtube_web_view.setVisibility(View.VISIBLE);*//*


             *//*   holder.ytPlayer.setVisibility(View.VISIBLE);
                    holder.ivPlay.setVisibility(View.GONE);
                    holder.video_thumbnail_image_view.setVisibility(View.GONE);
                    holder.ytPlayer.setVisibility(View.VISIBLE);
                    getLifecycle().addObserver(holder.ytPlayer);*//*

             *//*  holder.video_thumbnail_image_view.initialize( api_key, new YouTubeThumbnailView.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {

                            //when initialization is sucess, set the video id to thumbnail to load

                            youTubeThumbnailLoader.setVideo( data.get( position ).get( "video_code" ) );

                            youTubeThumbnailLoader.setOnThumbnailLoadedListener( new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                                @Override
                                public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                                    //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                                    youTubeThumbnailLoader.release();
                                }

                                @Override
                                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                                    //print or show error when thumbnail load failed
                                    Log.e( "error", "Youtube Thumbnail Error" );
                                }
                            } );
                        }

                        @Override
                        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                            //print or show error when initialization failed
                            Log.e( "Failure", "Youtube Initialization Failure" );

                        }
                    } );
*//*

             *//*
                    myViewHolder.youTubePlayerView.initialize(
                            initializedYouTubePlayer -> initializedYouTubePlayer.addListener(
                                    new AbstractYouTubePlayerListener() {
                                        @Override
                                        public void onReady() {
                                            initializedYouTubePlayer.play();
                                            initializedYouTubePlayer.loadVideo(arrayList1.get(i).get("youtubeKey"), 0);

                                        }
                                    }), true);
*//*






                }
            });*/

            if (data != null) {
                String vcode = data.get(position).get("video_code");
                holder.ytPlayer.initialize(new YouTubePlayerInitListener() {
                    public void onInitSuccess(@NonNull com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer initializedYouTubePlayer) {
                        initializedYouTubePlayer.addListener(
                                new AbstractYouTubePlayerListener() {
                                    @Override
                                    public void onReady() {
                                        Player = initializedYouTubePlayer;
                                        initializedYouTubePlayer.cueVideo(vcode, 0);
                                        initializedYouTubePlayer.play();
                                    }
                                });
                    }
                }, true);


            }
        }

        public int getItemCount() {
            return data.size();
        }
    }

    private class chatHolder extends RecyclerView.ViewHolder {
        /* YouTubePlayerView ytPlayer;*/
        YouTubePlayerView ytPlayer;
        YouTubeThumbnailView video_thumbnail_image_view;
        ImageView ivThumbnail, ivPlay;

        public chatHolder(View itemView) {
            super(itemView);
            ytPlayer = itemView.findViewById(R.id.youtube_view);
           /* video_thumbnail_image_view = itemView.findViewById(R.id.video_thumbnail_image_view);
            ivPlay = itemView.findViewById(R.id.ivPlay);*/

        }
    }

}
