package com.ualr.recyclerviewassignment.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InboxViewModel extends ViewModel {
    private MutableLiveData<List<Inbox>> inboxes;
    private MutableLiveData<Integer> selectedInbox = new MutableLiveData<>(-1);

    public MutableLiveData<List<Inbox>> getInboxes() {
        if (inboxes == null) {
            inboxes = new MutableLiveData<>(new ArrayList<>());
        }
        return inboxes;
    }

    public Integer getSelectedInboxIndex() {
        return selectedInbox.getValue();
    }

    public Inbox getSelectedInbox() {
        if (getSelectedInboxIndex() != -1) {
           return getInboxes().getValue().get(getSelectedInboxIndex());
        }
        return null;
    }

    public void setSelectedInbox(Integer inbox) {
        selectedInbox.setValue(inbox);
    }

    public void deselectInbox() {
        selectedInbox.setValue(-1);
    }

    public void addInbox(Inbox inbox) {
        List<Inbox> newInboxes = new ArrayList<>(getInboxes().getValue());
        newInboxes.add(0, inbox);

        inboxes.setValue(newInboxes);
    }

    public void addInbox(List<Inbox> inboxList) {
        List<Inbox> newInboxes = new ArrayList<>(getInboxes().getValue());
        newInboxes.addAll(inboxList);

        inboxes.setValue(newInboxes);
    }

    public void removeInbox(int position) {
        List<Inbox> newInboxes = new ArrayList<>(getInboxes().getValue());
        Inbox inbox = newInboxes.remove(position);

        inboxes.setValue(newInboxes);
    }

    public Integer removeSelectedInbox() {
        Integer selectedInbox = getSelectedInboxIndex();
        if (getSelectedInboxIndex() != -1) {
            removeInbox(selectedInbox);
            deselectInbox();
        }

        return selectedInbox;
    }
}
