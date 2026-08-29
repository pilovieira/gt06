package br.com.pilovieira.gt06.log;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;

import br.com.pilovieira.gt06.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment {

    private View view;
    private ListView logList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentInfoBinding binding = FragmentInfoBinding.inflate(inflater, container, false);
        this.view = binding.getRoot();
        logList = binding.listLog;

        binding.btnLogClear.setOnClickListener(view -> logClearClick());

        mountLogsList();

        return view;
    }

    private void mountLogsList() {
        List<ServerLog> logs = new ServerLogManager(getContext()).getLogs();
        logList.setAdapter(new LogListAdapter(getContext(), logs));
    }

    public void logClearClick() {
        new ServerLogManager(view.getContext()).clearLogs();
        mountLogsList();
    }

}
