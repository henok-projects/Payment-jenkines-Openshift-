package com.secpayment.payment.kafka;

import com.secpayment.payment.domain.Payment;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private final List<Payment> users = new ArrayList<>();

    public void add(Payment payment) {
        users.add(payment);
    }

    public void update(Payment pay) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getEmail().toString().equals(pay.getEmail().toString())) {
                users.get(i).setApprovalStatus(pay.getApprovalStatus());
            }
        }
    }

    public void clear() {
        users.remove(0);
    }

    public List<Payment> getAll() {
        return users;
    }
}
