package bisosad.kt2.service;

import bisosad.kt2.model.Shipment;
import bisosad.kt2.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    // Retrieve all shipments
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    // Retrieve a specific shipment by ID
    public Optional<Shipment> getShipmentById(Long id) {
        return shipmentRepository.findById(id);
    }

    // Create a new shipment
    public Shipment createShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    // Update an existing shipment
    public Shipment updateShipment(Long id, Shipment updatedShipment) {
        return shipmentRepository.findById(id).map(existingShipment -> {
            existingShipment.setAddress(updatedShipment.getAddress());
            existingShipment.setShippingMethod(updatedShipment.getShippingMethod());
            existingShipment.setStatus(updatedShipment.getStatus());
            return shipmentRepository.save(existingShipment);
        }).orElseThrow(() -> new RuntimeException("Shipment not found with ID: " + id));
    }

    // Delete a shipment
    public void deleteShipment(Long id) {
        if (shipmentRepository.existsById(id)) {
            shipmentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Shipment not found with ID: " + id);
        }
    }
}
