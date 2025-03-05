package bisosad.kt2.service;

import bisosad.kt2.model.Publisher;
import bisosad.kt2.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Publisher not found"));
    }

    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public Publisher updatePublisher(Long id, Publisher updatedPublisher) {
        Publisher publisher = getPublisherById(id);
        publisher.setName(updatedPublisher.getName());
        publisher.setAddress(updatedPublisher.getAddress());
        publisher.setPhone(updatedPublisher.getPhone());
        return publisherRepository.save(publisher);
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }
}
