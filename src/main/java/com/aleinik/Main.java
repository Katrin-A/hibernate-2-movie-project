package com.aleinik;

import com.aleinik.config.SessionCreator;
import com.aleinik.dao.*;
import com.aleinik.entity.*;
import com.aleinik.enums.Feature;
import com.aleinik.enums.Rating;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.aleinik.enums.Feature.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private final CityDAO cityDAO = new CityDAO();
    private final AddressDAO addressDAO = new AddressDAO();
    private final StoreDAO storeDAO = new StoreDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final RentalDAO rentalDAO = new RentalDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private final InventoryDAO inventoryDAO = new InventoryDAO();
    private final PaymentDAO paymentDAO = new PaymentDAO();
    private final LanguageDAO languageDAO = new LanguageDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final ActorDAO actorDAO = new ActorDAO();
    private final FilmDAO filmDAO = new FilmDAO();

    public static void main(String[] args) {
        Main main = new Main();

        Customer customer = main.createCustomer();
        System.out.println(customer.getCustomerId());

        Rental rental = main.returnFilm(16050);

        main.addNewFilm();
        main.rentFilm();


    }

    private void addNewFilm() {
        Transaction transaction = null;
        String movieName = "The Matrix";
        String description = "The Matrix is sci-fi action film about a computer hacker " +
                "who discovers that everyday reality is actually a complex, shared computer simulation.";
        int rentalDuration = 6;
        int releaseYear = 1999;
        short duration = 136;
        BigDecimal rentalRate = BigDecimal.valueOf(4.99);
        BigDecimal replacementCost = BigDecimal.valueOf(20.99);

        try {

            transaction = SessionCreator.getSession().beginTransaction();
            Language language = languageDAO.getById(1);
            Set<Feature> features = Set.of(TRAILERS, COMMENTARIES);
            Rating rating = Rating.R;
            Set<Actor> actors = getActorsForMatrix();
            Set<Category> categories = getCategoryForMatrix();


            Film film = Film.builder()
                    .title(movieName.toUpperCase())
                    .language(language)
                    .releaseYear(releaseYear)
                    .rentalRate(rentalRate)
                    .specialFeatures(features)
                    .rentalDuration(rentalDuration)
                    .replacementCost(replacementCost)
                    .actors(actors)
                    .category(categories)
                    .description(description)
                    .rating(rating)
                    .length(duration)
                    .build();

            film = filmDAO.save(film);

            Store store = storeDAO.getById(1);

            Inventory inventory = Inventory.builder()
                    .film(film)
                    .store(store)
                    .build();
            inventoryDAO.save(inventory);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to save your boring film: " + movieName, e);
        }
    }

    private Set<Actor> getActorsForMatrix() {
        Set<Actor> cast = new HashSet<>();
        cast.add(actorDAO.findOrCreate("Keanu", "Reeves"));
        cast.add(actorDAO.findOrCreate("Carrie-Anne", "Moss"));
        cast.add(actorDAO.findOrCreate("Laurence", "Fishburne"));
        cast.add(actorDAO.findOrCreate("Hugo", "Weaving"));

        return cast;
    }

    private Set<Category> getCategoryForMatrix() {
        Set<Category> categories = new HashSet<>();
        categoryDAO.findByName("Sci-Fi").ifPresent(categories::add);
        categoryDAO.findByName("Action").ifPresent(categories::add);
        categoryDAO.findByName("New").ifPresent(categories::add);
        return categories;
    }


    private void rentFilm() {
        int inventoryId = 1;
        int customerId = 600;
        int staffId = 1;
        Transaction transaction = null;
        try {
            transaction = SessionCreator.getSession().beginTransaction();
            Customer customer = customerDAO.getById(customerId);
            Staff staff = staffDAO.getById(staffId);
            Inventory inventory = inventoryDAO.getById(inventoryId);

            Rental rental = Rental.builder()
                    .customer(customer)
                    .staff(staff)
                    .rentalDate(LocalDateTime.now())
                    .inventory(inventory)
                    .build();

            rental = rentalDAO.save(rental);

            Payment payment = Payment.builder()
                    .rental(rental)
                    .staff(staff)
                    .customer(customer)
                    .amount(inventory.getFilm().getRentalRate())
                    .paymentDate(LocalDateTime.now())
                    .build();

            paymentDAO.save(payment);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Rental could not be made: ", e);
        }
    }

    private Rental returnFilm(int id) {
        Transaction transaction = null;
        try {
            transaction = SessionCreator.getSession().beginTransaction();
            Rental rental = rentalDAO.getById(id);
            rental.setReturnDate(LocalDateTime.now());
            rental = rentalDAO.update(rental);
            transaction.commit();

            return rental;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();

            }
            throw new RuntimeException("Rental can not be returned: ", e);

        }
    }


    private Customer createCustomer() {
        Transaction transaction = null;
        try {
            transaction = SessionCreator.getSession().beginTransaction();
            City city = cityDAO.findOrCreate("Utrecht", "Netherlands");
            Store store = storeDAO.getById(1);

            Address address = Address
                    .builder()
                    .address("Bekkerstraat 48")
                    .phone("+310631768543")
                    .district("Utrecht")
                    .city(city)
                    .build();

            Customer customer = Customer
                    .builder()
                    .address(address)
                    .email(address.getPhone())
                    .store(store)
                    .firstName("Katie")
                    .lastName("Aleinik")
                    .isActive(true)
                    .build();

            customerDAO.save(customer);
            transaction.commit();
            return customer;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Customer could not be created", e);
        }
    }
}