package tyagi.shubham.customsearch.fragments;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import tyagi.shubham.customsearch.R;

public class DorkFragment extends Fragment {
    TextView inurl, intext, intitle, site, filetype, tags, include, exclude, others;
    TextView input;
    TextView result;
    CheckBox year,month,today;

    private String finalSearch = "";

    public DorkFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    private void initialize(View view) {
        inurl = view.findViewById(R.id.dork_inurl_ev);
        intext = view.findViewById(R.id.dork_intext_ev);
        intitle = view.findViewById(R.id.dork_intitle_ev);
        filetype = view.findViewById(R.id.dork_filetype_ev);
        site = view.findViewById(R.id.dork_site_ev);
        tags = view.findViewById(R.id.dork_tags_ev);
        include = view.findViewById(R.id.dork_include_ev);
        exclude = view.findViewById(R.id.dork_exclude_ev);
        others = view.findViewById(R.id.dork_other_ev);
        result = view.findViewById(R.id.dork_result);
        input = view.findViewById(R.id.dork_search_box);

        year=view.findViewById(R.id.cb_year);
        month=view.findViewById(R.id.cb_month);
        today=view.findViewById(R.id.cb_today);

       year.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               month.setChecked(false);
               today.setChecked(false);
           }
       });
       month.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               today.setChecked(false);
               year.setChecked(false);
           }
       });
       today.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               year.setChecked(false);
               month.setChecked(false);
           }
       });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dork, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initialize(view);

        input.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() != 1 || event.getRawX() < ((float) (input.getRight() - input.getCompoundDrawables()[2].getBounds().width()))) {
                    return false;
                }
                searchURL();
                return true;
            }
        });


    }

    private void searchURL() {
        saveToString();
        result.setText(finalSearch);
        String url = "https://www.google.com/search?q=" + finalSearch;
        Intent i = new Intent("android.intent.action.VIEW");
        i.setData(Uri.parse(url));
        getActivity().startActivity(i);
    }

    private void saveToClipboard() {
        ((ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Search Query", finalSearch));
        ;
    }

    private void saveToString() {
        finalSearch = "";
        if (input.getText().toString().trim().length() != 0) {
            finalSearch += " " + input.getText().toString();
        }
        if (intitle.getText().toString().trim().length() != 0) {
            finalSearch += " intitle:" + intitle.getText().toString();
        }
        if (inurl.getText().toString().trim().length() != 0) {
            finalSearch += " inurl:" + inurl.getText().toString();
        }
        if (intext.getText().toString().trim().length() != 0) {
            finalSearch += " intext:" + intext.getText().toString();
        }
        if (site.getText().toString().trim().length() != 0) {
            finalSearch += " site:" + site.getText().toString();
        }

        if (filetype.getText().toString().trim().length() != 0) {
            finalSearch += " filetype:" + filetype.getText().toString();
        }
        if (include.getText().toString().trim().length() != 0) {
            if (include.getText().toString().contains(" ")) {
                finalSearch += " +" + this.include.getText().toString().replace(" ", " +");
            } else {
                finalSearch += " +" + this.include.getText().toString();
            }
        }
        if (exclude.getText().toString().trim().length() != 0) {
            if (exclude.getText().toString().contains(" ")) {
                finalSearch += " -" + this.exclude.getText().toString().replace(" ", " +");
            } else {
                finalSearch += " -" + this.exclude.getText().toString();
            }
        }
        if (others.getText().toString().trim().length() != 0) {
            finalSearch += " " + others.getText().toString();
        }
        if (tags.getText().toString().trim().length() != 0) {
            if (tags.getText().toString().contains(" ")) {
                finalSearch += " (" + tags.getText().toString().replace(" ", "|") + ")";

            }else {
                finalSearch += " (" + tags.getText().toString() + ")";
            }
        }
        if(year.isChecked()){
            finalSearch+="&tbs=qdr:y";
        }
        if(month.isChecked()){
            finalSearch+="&tbs=qdr:y";
        }
        if(today.isChecked()){
            finalSearch+="&tbs=qdr:y";
        }
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dork_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_dork_copy:
                saveToString();
                saveToClipboard();
                result.setText(finalSearch);
                break;
            case R.id.action_dork:

                searchURL();
        }
        return true;
    }


}
