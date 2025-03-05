package bisosad.kt2.service;

import bisosad.kt2.model.MobilePhone;
import bisosad.kt2.repository.MobilePhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MobilePhoneService {

    @Autowired
    private MobilePhoneRepository mobilePhoneRepository;

    public List<MobilePhone> getAllMobilePhones() {
        return mobilePhoneRepository.findAll();
    }

    public MobilePhone getMobilePhoneById(Long id) {
        return mobilePhoneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("MobilePhone not found"));
    }

    public MobilePhone createMobilePhone(MobilePhone mobilePhone) {
        return mobilePhoneRepository.save(mobilePhone);
    }

    public MobilePhone updateMobilePhone(Long id, MobilePhone updatedMobilePhone) {
        MobilePhone mobilePhone = getMobilePhoneById(id);
        mobilePhone.setModel(updatedMobilePhone.getModel());
        mobilePhone.setBrand(updatedMobilePhone.getBrand());
        return mobilePhoneRepository.save(mobilePhone);
    }

    public void deleteMobilePhone(Long id) {
        mobilePhoneRepository.deleteById(id);
    }
}
