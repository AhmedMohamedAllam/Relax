package com.allam.relax.view;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.allam.relax.R;
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
    private static long profileID = 1;
    private static long ordersID = 2;
    private static long facebookID = 3;
    private static long whatsappID = 4;
    private static long callID = 5;
    private static long googlePlayID = 6;

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

    public static void getDrawer(final Activity activity, Toolbar toolbar) {

        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem profile = new PrimaryDrawerItem().withIdentifier(profileID).withName(R.string.profile).withIcon(R.drawable.profile_icon);
        PrimaryDrawerItem myOrders = new PrimaryDrawerItem().withIdentifier(ordersID).withName(R.string.my_orders).withIcon(R.drawable.orders_icon);


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
                .addDrawerItems(
                        profile,
                        myOrders,
                        new SectionDrawerItem().withName("Contact us"),
                        facebookItem,
                        whtsappItem,
                        callUsItem,
                        new SectionDrawerItem().withName("Rate us"),
                        googlePlayItem
                        )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        Toast.makeText(activity, drawerItem.getIdentifier() + "", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withTranslucentStatusBar(true)
                .withDisplayBelowStatusBar(true)
                .withSelectedItem(-1)
                .build();

    }
}
