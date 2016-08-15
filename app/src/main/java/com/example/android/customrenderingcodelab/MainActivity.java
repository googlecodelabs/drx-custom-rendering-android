/*
 * Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.customrenderingcodelab;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdLoader adLoader = new AdLoader.Builder(getApplicationContext(),
                getString(R.string.native_ad_unit_id))
                .forCustomTemplateAd(getString(R.string.native_template_id),
                        new NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener() {
                            @Override
                            public void onCustomTemplateAdLoaded(NativeCustomTemplateAd ad) {
                                ViewGroup adView = (ViewGroup) findViewById(R.id.adView);
                                displayCustomTemplateAd(adView, ad);
                            }
                        },
                        null)
                .build();

        adLoader.loadAd(new PublisherAdRequest.Builder().build());
    }

    private void displayCustomTemplateAd (ViewGroup parent, final NativeCustomTemplateAd ad) {
        // Inflate a layout and add it to the parent ViewGroup.
        LayoutInflater inflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View adView = inflater.inflate(R.layout.custom_template_ad, parent);

        // Show the custom template
        TextView headline = (TextView) findViewById(R.id.headline);
        TextView caption = (TextView) findViewById(R.id.caption);
        ImageView mainImage = (ImageView) findViewById(R.id.mainImage);
        headline.setText(ad.getText("Headline"));
        caption.setText(ad.getText("Caption"));
        mainImage.setImageDrawable(ad.getImage("MainImage").getDrawable());

        // Record an impression
        ad.recordImpression();

        // Handle clicks on image
        mainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.performClick("MainImage");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
