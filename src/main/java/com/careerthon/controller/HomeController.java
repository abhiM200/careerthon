import com.careerthon.service.SpecExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final UserStoryRepository userStoryRepository;
    private final SpecExportService specExportService;

    public HomeController(UserStoryRepository userStoryRepository, SpecExportService specExportService) {
        this.userStoryRepository = userStoryRepository;
        this.specExportService = specExportService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<UserStory> allStories = userStoryRepository.findAll();
        model.addAttribute("testimonials", allStories.stream().filter(this::isTestimonial).collect(Collectors.toList()));
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        List<UserStory> allStories = userStoryRepository.findAll();
        model.addAttribute("team", allStories.stream().filter(this::isTeamMember).collect(Collectors.toList()));
        return "about";
    }

    @GetMapping("/features")
    public String features() {
        return "features";
    }

    @GetMapping("/developer/export-spec")
    public ResponseEntity<byte[]> exportSpec() throws IOException {
        byte[] docx = specExportService.generateMasterSpec();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=careerthon_master_spec.docx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(docx);
    }

    private boolean isTeamMember(UserStory story) {
        return story.getAvatarUrl() != null && !story.getAvatarUrl().isEmpty();
    }

    private boolean isTestimonial(UserStory story) {
        return story.getAvatarUrl() == null || story.getAvatarUrl().isEmpty();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/profile")
    public String profile() {
        // For now, redirect to home or show a simple profile
        return "index";
    }
}
