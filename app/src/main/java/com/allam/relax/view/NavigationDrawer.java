package com.allam.relax.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.allam.relax.R;
import com.allam.relax.view.activity.HomeActivity;
import com.allam.relax.view.activity.LoginActivity;
import com.allam.relax.view.activity.OrderActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

/**
 * Relax
 * Created by Allam on 20/03/2018 .
 */

public class NavigationDrawer {
    public static final int NONE = -1;
    public static final int profileID = 1;
    public static final int myOrdersID = 2;
    public static final int signOutID = 3;
    public static final int facebookID = 4;
    public static final int whatsappID = 5;
    public static final int callID = 6;
    public static final int googlePlayID = 7;
    public static final int newOrderID = 8;

    private static ProfileDrawerItem getCurrentProfile(final Activity activity) {
        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        ProfileDrawerItem drawerProfile = new ProfileDrawerItem();

        drawerProfile.withEmail(mCurrentUser.getEmail());
        drawerProfile.withName(mCurrentUser.getDisplayName());
        drawerProfile.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                return false;
            }
        });

        return drawerProfile;
    }

    public static void getDrawer(final Activity activity, Toolbar toolbar, int selectedID) {

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem profile = new PrimaryDrawerItem().withIdentifier(profileID).withName(R.string.profile).withIcon(R.drawable.profile_icon);
        PrimaryDrawerItem newOrder = new PrimaryDrawerItem().withIdentifier(newOrderID).withName(R.string.new_order).withIcon(R.drawable.add_icon);
        PrimaryDrawerItem myOrders = new PrimaryDrawerItem().withIdentifier(myOrdersID).withName(R.string.my_orders).withIcon(R.drawable.orders_icon);
        PrimaryDrawerItem signOut = new PrimaryDrawerItem().withIdentifier(signOutID).withName(R.string.sign_out).withIcon(R.drawable.signout_icon);


        SecondaryDrawerItem facebookItem = new SecondaryDrawerItem().withIdentifier(facebookID).withName(R.string.facebook).withIcon(R.drawable.facebook_icon);
        SecondaryDrawerItem whtsappItem = new SecondaryDrawerItem().withIdentifier(whatsappID).withName(R.string.whatsapp).withIcon(R.drawable.whatsapp_icon);
        SecondaryDrawerItem googlePlayItem = new SecondaryDrawerItem().withIdentifier(googlePlayID).withName(R.string.google_play).withIcon(R.drawable.gplay_icon);
        SecondaryDrawerItem callUsItem = new SecondaryDrawerItem().withIdentifier(callID).withName(R.string.call_us).withIcon(R.drawable.call_icon);


        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.background)
                .addProfiles(getCurrentProfile(activity))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

//create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .addDrawerItems(profile, newOrder, myOrders, signOut,
                        new SectionDrawerItem().withName("Contact us"),
                        facebookItem, whtsappItem, callUsItem,
                        new SectionDrawerItem().withName("Rate us"),
                        googlePlayItem
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        int identifier = (int) drawerItem.getIdentifier();
                        switch (identifier) {
                            case profileID:
                                onProfileClick();
                                Toast.makeText(activity, "Soon!", Toast.LENGTH_SHORT).show();
                                return true;
                            case newOrderID:
                                onNewOrderClick(activity);
                                return true;
                            case myOrdersID:
                                onMyOrdersClick();
                                Toast.makeText(activity, "Soon!", Toast.LENGTH_SHORT).show();
                                return true;
                            case signOutID:
                                onSignOutClick(activity);
                                return true;
                            case facebookID:
                                onFacebookClick();
                                Toast.makeText(activity, "Soon!", Toast.LENGTH_SHORT).show();
                                return true;
                            case whatsappID:
                                onWhatsAppClick();
                                Toast.makeText(activity, "Soon!", Toast.LENGTH_SHORT).show();
                                return true;
                            case callID:
                                onCallUsClick();
                                Toast.makeText(activity, "Soon!", Toast.LENGTH_SHORT).show();
                                return true;
                            case googlePlayID:
                                onGooglePlayClick();
                                Toast.makeText(activity, "Soon!", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                })
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(true)
                .withDisplayBelowStatusBar(true)
                .withSelectedItem(selectedID)
                .build();
    }

    private static void onProfileClick() {

    }

    private static void onNewOrderClick(Activity activity) {
        Intent intent = new Intent(activity, OrderActivity.class);
        activity.startActivity(intent);
    }

    private static void onMyOrdersClick() {
    }

    private static void onSignOutClick(Activity activity) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut(); //facebook Logout
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    private static void onFacebookClick() {

    }

    private static void onWhatsAppClick() {

    }

    private static void onCallUsClick() {

    }

    private static void onGooglePlayClick() {

    }
}
