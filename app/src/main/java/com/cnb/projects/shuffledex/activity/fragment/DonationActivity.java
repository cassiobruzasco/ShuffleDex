package com.cnb.projects.shuffledex.activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cnb.projects.shuffledex.R;
import com.cnb.projects.shuffledex.billing.IabHelper;
import com.cnb.projects.shuffledex.billing.IabResult;
import com.cnb.projects.shuffledex.billing.Inventory;
import com.cnb.projects.shuffledex.billing.Purchase;

/**
 * Created by Cássio on 29-Jan-16.
 */
public class DonationActivity extends AppCompatActivity {

    CardView firstCard;
    CardView secondCard;
    CardView thirdCard;
    String donation00 = "donation_000";
    String donation01 = "donation_001";
    String donation02 = "donation_002";
    String donation03 = "donation_003";
    IabHelper mHelper;
    String base64EncodedPublicKey;
    private static final String TAG =
            "com.cnb.projects.shuffledex";
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener;
    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener;
    private final String purchase_donation_000 = "donation 000";
    private final String purchase_donation_001 = "donation 001";
    private final String purchase_donation_002 = "donation 002";
    private final String purchase_donation_003 = "donation 003";
    private int mProductFlag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation_layout);

        base64EncodedPublicKey = getResources().getString(R.string.merchant_key);

        firstCard = (CardView)findViewById(R.id.card_view);
        secondCard = (CardView)findViewById(R.id.card_view_2);
        thirdCard = (CardView)findViewById(R.id.card_view_3);

        implementClicks();
        initBilling();
        purchaseListeners();

    }

    private void purchaseListeners(){

        /**
         * Consume Finished
         */
        mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
            public void onConsumeFinished(Purchase purchase, IabResult result) {
                if (result.isSuccess()) {
                    firstCard.setEnabled(true);
                    Toast.makeText(DonationActivity.this, getResources().getString(R.string.thankfull), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DonationActivity.this, "Purchased failed, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        };

        /**
         * Query Inventory Finished
         */
        mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                if (result.isFailure()) {
                } else {
                    switch (mProductFlag){
                        case 1:
                            mHelper.consumeAsync(inventory.getPurchase(donation00),
                                    mConsumeFinishedListener);
                            break;
                        case 2:
                            mHelper.consumeAsync(inventory.getPurchase(donation01),
                                    mConsumeFinishedListener);
                            break;
                        case 3:
                            mHelper.consumeAsync(inventory.getPurchase(donation02),
                                    mConsumeFinishedListener);
                            break;
                        default:
                            break;
                    }

                }
            }
        };

        /**
         * Purchase Finished
         */
        mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
            @Override
            public void onIabPurchaseFinished(IabResult result, Purchase info) {
                if (result.isFailure()) {
                    return;
                }
                else if (info.getSku().equals(donation00)) {
                    consumeItem();
                    firstCard.setEnabled(false);
                    Toast.makeText(DonationActivity.this, getResources().getString(R.string.thankfull), Toast.LENGTH_SHORT).show();
                } else if (info.getSku().equals(donation01)) {
                    consumeItem();
                    secondCard.setEnabled(false);
                    Toast.makeText(DonationActivity.this, getResources().getString(R.string.thankfull), Toast.LENGTH_SHORT).show();
                } else if (info.getSku().equals(donation02)) {
                    consumeItem();
                    thirdCard.setEnabled(false);
                    Toast.makeText(DonationActivity.this, getResources().getString(R.string.thankfull), Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    private void initBilling() {
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d(TAG, "In-app Billing setup failed: " + result);
                } else {
                    Log.d(TAG, "In-app Billing is set up OK");
                }
            }
        });
    }

    public void consumeItem() {
        mHelper.queryInventoryAsync(mReceivedInventoryListener);
    }

    private void implementClicks() {

        /**
         * First Card click
         */
        firstCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.launchPurchaseFlow(DonationActivity.this, donation00, 10000,
                        mPurchaseFinishedListener, purchase_donation_000);
                mProductFlag = 1;
            }
        });

        /**
         * second card
         */
        secondCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.launchPurchaseFlow(DonationActivity.this, donation01, 10001,
                        mPurchaseFinishedListener, purchase_donation_001);
                mProductFlag = 2;
            }
        });

        /**
         * third card
         */
        thirdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHelper.launchPurchaseFlow(DonationActivity.this, donation02, 10002,
                        mPurchaseFinishedListener, purchase_donation_002);
                mProductFlag = 3;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) mHelper.dispose();
        mHelper = null;
    }
}
