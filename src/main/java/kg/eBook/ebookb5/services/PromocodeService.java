package kg.eBook.ebookb5.services;

import kg.eBook.ebookb5.dto.requests.PromocodeRequest;
import kg.eBook.ebookb5.dto.responses.SimplyResponse;
import kg.eBook.ebookb5.exceptions.AlreadyExistException;
import kg.eBook.ebookb5.exceptions.InvalidDateException;
import kg.eBook.ebookb5.models.*;
import kg.eBook.ebookb5.repositories.PromocodeRepository;
import kg.eBook.ebookb5.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromocodeService {

    private final PromocodeRepository promocodeRepository;
    private final UserRepository userRepository;

    public SimplyResponse createPromoCode(PromocodeRequest promoCodeRequest, Authentication authentication) {

        if (promoCodeRequest.getDateOfStart().isAfter(promoCodeRequest.getDateOfFinish())) {
            throw new InvalidDateException("The date you wrote is earlier than the start date");
        } else if (promoCodeRequest.getDateOfStart().plusDays(1L).isAfter(promoCodeRequest.getDateOfFinish())) {
            throw new InvalidDateException(
                    "The difference between the start and end of the promo code must be more than 1 day");
        }

        if (promocodeRepository.existsByName(promoCodeRequest.getName())) {
            throw new AlreadyExistException("Already exists with the same name");
        }

        Promocode promocode = new Promocode(promoCodeRequest);

        User user = userRepository.findByEmail(authentication.getName()).get();

        promocode.setVendor(user);

        promocodeRepository.save(promocode);

        SimplyResponse response = new SimplyResponse("Promo code saved successfully!");
        System.out.println("ITss croos");

        return response;
    }



//    public Promocode findByName(String name){
//
//        return promoCodeRepository.findPromoCodeByName(name).orElseThrow(
//                ()-> new NotFoundException(
//                        "Promo code with name = " + " not found"));
//    }
//
//    public User discount(String promoName, Long clientId) {
//
//        Promocode promocode = findByName(promoName);
//
//        User user = userRepository.findById(clientId).get();
//
//        for (Book book : user.getBasket()) {
//            for (PromoCode code : book.getOwner().getPromoCodes()) {
//                if (code.equals(promoCode)) {
//                    if (book.getDiscount() <= 0) {
//                        int bookPrice = book.getPrice();
//                        int priceDiscount = bookPrice * promoCode.getDiscount() / 100;
//                        int newPrice = bookPrice - priceDiscount;
//                        book.setPrice(newPrice);
//                    }
//                }
//            }
//        }
//        return user;
//    }
}
