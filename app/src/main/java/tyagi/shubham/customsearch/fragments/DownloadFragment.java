package tyagi.shubham.customsearch.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import tyagi.shubham.customsearch.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment implements CompoundButton.OnCheckedChangeListener{

    private static final String GOOGLE_IMAGE_SEARCH_URL = "https://www.google.com/search?tbm=isch&q=";
    private static final String GOOGLE_SEARCH_URL = "https://www.google.com/search?q=";
    private boolean[] checkboxCheck = new boolean[]{false, false, false, false, false, false, false};
    private String[] extensionArray = new String[]{"", "mkv|mp4|avi|mov|mpg|wmv|3gp|", "mp3|wav|ac3|ogg|flac|wma|m4a|", "jpg|png|bmp|gif|tif|tiff|psd|", "gif|", "MOBI|CBZ|CBR|CBC|CHM|EPUB|FB2|LIT|LRF|ODT|PDF|PRC|PDB|PML|RB|RTF|TCR|DOC|DOCX|", "exe|iso|tar|rar|zip|apk|"};
    private static final String inUrlPages=" -inurl:(jsp|pl|php|html|aspx|htm|cf|shtml) intitle:index of";
    private static final String inUrlAudio=" -inurl:(listen77|mp3raid|mp3toss|mp3drug|index_of|wallywashis) ";
    private EditText searchText;

    private CheckBox  cbAll,cbVdo,cbAudio,cbSoftware,cbGIF,cbImg,cbBooks;
    public DownloadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_download, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        searchText=view.findViewById(R.id.search_download);
        init(view);
        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() != 1 || event.getRawX() < ((float) (searchText.getRight() - searchText.getCompoundDrawables()[2].getBounds().width()))) {
                    return false;
                }
                String url = "";
                String query = searchText.getText().toString();
                String extensions = "";
                int totalCheckedBoxes = 0;
                boolean imageChecked = false;

                for (int i = 1; i < extensionArray.length; i++) {
                    if (checkboxCheck[i]) {
                        extensions = extensions + extensionArray[i];
                        totalCheckedBoxes++;
                        if (i == 3) {
                            imageChecked = true;
                        }
                    }
                }
                if (extensions.length() > 0) {
                    extensions = extensions.substring(0, extensions.length() - 1);
                }
                if (query.trim().length() != 0) {
                    if (checkboxCheck[0] || extensions.length() == 0) {
                        url=url+query+inUrlPages;
                        if(checkboxCheck[2]) {
                            url = url + inUrlAudio;
                        }

                    }else {
                        url = url + query + " +(" + extensions + ")"+inUrlPages;
                        if(checkboxCheck[2]){
                            url = url + inUrlAudio;
                        }

                    }
                    url = Uri.encode(url);
                    if (totalCheckedBoxes == 1 && imageChecked) {
                        getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(GOOGLE_IMAGE_SEARCH_URL + url)));
                    } else {
                        getActivity().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(GOOGLE_SEARCH_URL + url)));
                    }
                }
                return true;

            }
        });
    }

    private void init(View view) {
    cbAll=view.findViewById(R.id.cbAll);
    cbAudio=view.findViewById(R.id.cbAudio);
    cbBooks=view.findViewById(R.id.cbBooks);
    cbGIF=view.findViewById(R.id.cbGIF);
    cbImg=view.findViewById(R.id.cbImage);
    cbVdo=view.findViewById(R.id.cbVdo);
    cbSoftware=view.findViewById(R.id.cbSoftware);

    cbSoftware.setOnCheckedChangeListener(this);
    cbAudio.setOnCheckedChangeListener(this);
    cbAll.setOnCheckedChangeListener(this);
    cbBooks.setOnCheckedChangeListener(this);
    cbGIF.setOnCheckedChangeListener(this);
    cbImg.setOnCheckedChangeListener(this);
    cbVdo.setOnCheckedChangeListener(this);

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int index = Integer.parseInt(compoundButton.getTag().toString());
        this.checkboxCheck[index] = compoundButton.isChecked();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_download_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
