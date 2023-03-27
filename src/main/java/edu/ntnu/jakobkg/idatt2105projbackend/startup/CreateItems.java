package edu.ntnu.jakobkg.idatt2105projbackend.startup;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Item;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This class is run on application start and creates some example items
 */
@Component
public class CreateItems implements CommandLineRunner {
    @Autowired
    ItemRepository itemRepo;

    @Override
    public void run(String... args) {
        if (itemRepo.count() == 0) {

            itemRepo.save(new Item(
                "Plante", "Veldig fin plante", "24.03.23",
                "63.404734992005245", "10.371198931159565", "7049 Trondheim",
                "50", 6,
                "https://media.houseandgarden.co.uk/photos/618944690a583de660124d52/master/w_1600%2Cc_limit/1-house-29mar17-Nick-Pope_b.jpg, https://www.bhg.com/thmb/SfvVALaQxFyi4vYdbhBR11S41S8=/1280x0/filters:no_upscale():strip_icc()/indoor-potted-houseplants-703b321a-81cf8e1f9aee48a28e1be3bbc45e4386.jpg, https://assets.vogue.in/photos/5d95b26ed3535f0008d721cd/2:3/w_2560%2Cc_limit/f.jpg",
                2
                ));
                itemRepo.save(new Item("Maleri", "Fint maleri", "22.03.23",
                "63.4106518513291", "10.422525681017316", "7049 Trondheim",
                "6000", 2,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/c/ce/Theodor_Kittelsen_-_Far%2C_far_away_Soria_Moria_Palace_shimmered_like_Gold_-_Google_Art_Project.jpg/1920px-Theodor_Kittelsen_-_Far%2C_far_away_Soria_Moria_Palace_shimmered_like_Gold_-_Google_Art_Project.jpg",
                2
                ));
            }
    }
}