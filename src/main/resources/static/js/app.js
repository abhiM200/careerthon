/* ============================================
   CAREERTHON - Main JavaScript
   ============================================ */

// ─── Mobile Menu ────────────────────────────
function toggleMobileMenu() {
  const menu = document.getElementById('mobile-menu');
  if (menu) menu.classList.toggle('open');
}

// ─── Scroll Animations (Intersection Observer) ──
function initScrollAnimations() {
  const elements = document.querySelectorAll('[data-animate]');
  if (!elements.length) return;

  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry, i) => {
      if (entry.isIntersecting) {
        const delay = entry.target.dataset.delay || 0;
        setTimeout(() => {
          entry.target.classList.add('animated');
        }, parseInt(delay));
        observer.unobserve(entry.target);
      }
    });
  }, { threshold: 0.12 });

  elements.forEach(el => observer.observe(el));
}

// ─── Sticky Banner ──────────────────────────
function initStickyBanner() {
  const banner = document.getElementById('sticky-banner');
  if (!banner) return;

  let shown = false;
  window.addEventListener('scroll', () => {
    if (!shown && window.scrollY > 400) {
      banner.classList.add('visible');
      shown = true;
    }
  });
}

// ─── FAQ Accordion ──────────────────────────
function toggleFaq(button) {
  const answer = button.nextElementSibling;
  const icon = button.querySelector('.faq-icon');
  const isOpen = answer.classList.contains('open');

  // Close all
  document.querySelectorAll('.faq-answer').forEach(a => a.classList.remove('open'));
  document.querySelectorAll('.faq-icon').forEach(i => { i.textContent = '+'; i.style.transform = ''; });

  // Toggle current
  if (!isOpen) {
    answer.classList.add('open');
    if (icon) { icon.textContent = '−'; icon.style.transform = 'rotate(180deg)'; }
  }
}

// ─── Score Counter Animation ─────────────────
function animateCounters() {
  const scoreEls = document.querySelectorAll('.gauge-number, .score-number');
  scoreEls.forEach(el => {
    const target = parseInt(el.textContent) || 0;
    let start = 0;
    const duration = 1800;
    const step = (timestamp) => {
      if (!start) start = timestamp;
      const progress = Math.min((timestamp - start) / duration, 1);
      const ease = progress < 0.5 ? 2 * progress * progress : -1 + (4 - 2 * progress) * progress;
      el.textContent = Math.round(ease * target);
      if (progress < 1) requestAnimationFrame(step);
    };
    requestAnimationFrame(step);
  });
}

// ─── Analyzing Page Logic ────────────────────
function startAnalysis() {
  const reviewId = document.getElementById('review-id')?.value;
  if (!reviewId) return;

  const progressBar = document.getElementById('progress-bar');
  const progressLabel = document.getElementById('progress-label');
  const steps = [
    { id: 'step-1', label: '✅ Profile data fetched', delay: 400, progress: 15 },
    { id: 'step-2', label: '✅ Headline & about section analyzed', delay: 900, progress: 30 },
    { id: 'step-3', label: '✅ ATS optimization check complete', delay: 1400, progress: 48 },
    { id: 'step-4', label: '✅ Keyword density scanned', delay: 1900, progress: 62 },
    { id: 'step-5', label: '✅ All 15 section scores calculated', delay: 2400, progress: 78 },
    { id: 'step-6', label: '✅ Personalized recommendations generated', delay: 2900, progress: 92 },
    { id: 'step-7', label: '✅ Report ready!', delay: 3300, progress: 100 }
  ];

  // Set step 1 active immediately
  setStepActive('step-1');

  steps.forEach((step, index) => {
    setTimeout(() => {
      // Mark previous done
      if (index > 0) setStepDone(steps[index - 1].id, steps[index - 1].label);
      // Set current active
      if (index < steps.length - 1) setStepActive(step.id);
      else setStepDone(step.id, step.label);

      // Update progress
      if (progressBar) progressBar.style.width = step.progress + '%';
      if (progressLabel) progressLabel.textContent = step.label;
    }, step.delay);
  });

  // Trigger backend analysis then redirect
  setTimeout(() => {
    fetch('/review/analyze/' + reviewId)
      .then(res => res.json())
      .then(data => {
        if (data.redirectUrl) {
          setTimeout(() => { window.location.href = data.redirectUrl; }, 600);
        }
      })
      .catch(() => {
        // Fallback direct redirect
        setTimeout(() => { window.location.href = '/report/' + reviewId; }, 1000);
      });
  }, 1500);
}

function setStepActive(id) {
  const el = document.getElementById(id);
  if (el) { el.classList.remove('pending'); el.classList.add('active'); }
}

function setStepDone(id, label) {
  const el = document.getElementById(id);
  if (el) { el.classList.remove('active', 'pending'); el.classList.add('done'); if (label) el.textContent = label; }
}

// ─── Form Validation ────────────────────────
function initFormValidation() {
  const form = document.getElementById('review-form');
  if (!form) return;

  form.addEventListener('submit', (e) => {
    const urlInput = document.getElementById('linkedinUrl');
    const btn = document.getElementById('review-submit-btn');
    if (!urlInput || !urlInput.value.trim()) return;

    if (btn) {
      btn.innerHTML = '<span class="btn-text">Analyzing...</span><span class="btn-icon">⏳</span>';
      btn.disabled = true;
    }
  });

  // Real-time URL feedback
  const urlInput = document.getElementById('linkedinUrl');
  if (urlInput) {
    urlInput.addEventListener('input', () => {
      const val = urlInput.value.trim();
      const isValid = /https?:\/\/(www\.)?linkedin\.com\/in\/.+/.test(val);
      urlInput.style.borderColor = val.length > 10
        ? (isValid ? 'var(--accent-green)' : 'var(--accent-orange)')
        : '';
    });
  }
}

// ─── Email Report ────────────────────────────
function sendReportEmail() {
  const emailInput = document.getElementById('email-input');
  const btn = document.getElementById('email-send-btn');
  const successDiv = document.getElementById('email-success');
  const reviewId = document.getElementById('report-review-id')?.value;

  if (!emailInput || !emailInput.value.trim()) {
    emailInput.focus();
    emailInput.style.border = '2px solid var(--accent-red)';
    return;
  }

  if (btn) { btn.textContent = 'Sending...'; btn.disabled = true; }

  fetch('/report/' + reviewId + '/email', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: 'email=' + encodeURIComponent(emailInput.value)
  })
  .then(res => res.json())
  .then(data => {
    if (successDiv) { successDiv.style.display = 'block'; }
    if (btn) { btn.textContent = 'Sent! ✅'; }
  })
  .catch(() => {
    if (successDiv) { successDiv.textContent = '✅ Report queued for delivery!'; successDiv.style.display = 'block'; }
    if (btn) { btn.textContent = 'Sent! ✅'; }
  });
}

// ─── Shareable Scorecard ─────────────────────
function generateScorecard() {
  const element = document.getElementById('scorecard-template');
  const btn = event.currentTarget;
  if (!element || !window.html2canvas) return;

  const originalBtnText = btn.innerHTML;
  btn.innerHTML = '<span>⏳ Generating...</span>';
  btn.disabled = true;

  // Use a slight timeout to ensure styles are ready
  setTimeout(() => {
    html2canvas(element, {
      scale: 2, // High quality
      useCORS: true,
      backgroundColor: '#0f172a'
    }).then(canvas => {
      const link = document.createElement('a');
      link.download = 'Careerthon_LinkedIn_Score.png';
      link.href = canvas.toDataURL('image/png');
      link.click();
      
      btn.innerHTML = '<span>✅ Downloaded!</span>';
      setTimeout(() => {
        btn.innerHTML = originalBtnText;
        btn.disabled = false;
      }, 3000);
    }).catch(err => {
      console.error('Scorecard error:', err);
      btn.innerHTML = originalBtnText;
      btn.disabled = false;
    });
  }, 100);
}

// ─── Header Scroll Effect ────────────────────
function initHeaderScroll() {
  const header = document.getElementById('site-header');
  if (!header) return;
  window.addEventListener('scroll', () => {
    if (window.scrollY > 50) {
      header.classList.add('scrolled');
    } else {
      header.classList.remove('scrolled');
    }
  });
}

// ─── Popup Logic ─────────────────────────────
function showPopup() {
  const popup = document.getElementById('promo-popup');
  const card = document.getElementById('promo-card');
  if (!popup || localStorage.getItem('careerthon_popup_shown')) return;

  popup.classList.remove('opacity-0', 'pointer-events-none');
  card.classList.remove('scale-90');
  card.classList.add('scale-100');
  localStorage.setItem('careerthon_popup_shown', 'true');
}

function closePopup() {
  const popup = document.getElementById('promo-popup');
  const card = document.getElementById('promo-card');
  if (popup) {
    popup.classList.add('opacity-0', 'pointer-events-none');
    card.classList.remove('scale-100');
    card.classList.add('scale-90');
  }
}

function initPopupLogic() {
  // Show after 8 seconds
  setTimeout(showPopup, 8000);

  // Exit intent
  document.addEventListener('mouseleave', (e) => {
    if (e.clientY < 0) showPopup();
  });
}

// ─── Init ────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
  initScrollAnimations();
  initStickyBanner();
  initFormValidation();
  initHeaderScroll();
  initPopupLogic();

  // Animate score counters on report page
  if (document.querySelector('.gauge-number')) {
    setTimeout(animateCounters, 400);
  }
});
