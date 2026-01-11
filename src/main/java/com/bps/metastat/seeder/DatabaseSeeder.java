package com.bps.metastat.seeder;

import com.bps.metastat.domain.entity.*;
import com.bps.metastat.domain.enums.*;
import com.bps.metastat.domain.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Set;

@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {
    
    private final OrganizationRepository organizationRepository;
    private final DomainRepository domainRepository;
    private final SubjectCategoryRepository subjectRepository;
    private final ConceptRepository conceptRepository;
    private final UserRepository userRepository;
    private final StatisticalActivityRepository activityRepository;
    private final VariableRepository variableRepository;
    private final PublicationRepository publicationRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) {
        if (activityRepository.count() > 0) {
            log.info("Database already seeded. Skipping...");
            return;
        }
        
        log.info("=== Starting Comprehensive Database Seeding ===");
        
        seedOrganizations();
        seedDomains();
        seedSubjectCategories();
        seedConcepts();
        seedUsers();
        seedStatisticalActivities();
        seedVariables();
        seedPublications();
        
        log.info("=== Database Seeding Completed Successfully ===");
        logSeedingSummary();
    }
    
    private void logSeedingSummary() {
        log.info("Organizations: {}", organizationRepository.count());
        log.info("Domains: {}", domainRepository.count());
        log.info("Subjects: {}", subjectRepository.count());
        log.info("Concepts: {}", conceptRepository.count());
        log.info("Users: {}", userRepository.count());
        log.info("Activities: {}", activityRepository.count());
        log.info("Variables: {}", variableRepository.count());
        log.info("Publications: {}", publicationRepository.count());
    }
    
    private void seedOrganizations() {
        log.info("Seeding Organizations...");
        
        Organization bpsRI = new Organization();
        bpsRI.setName("BPS RI");
        bpsRI.setEmail("bpshq@bps.go.id");
        bpsRI.setWebsite("https://www.bps.go.id");
        bpsRI.setType(OrganizationType.CENTRAL_NSO);
        bpsRI.setAddress("Jl. Dr. Sutomo No.6-8, Jakarta Pusat 10710");
        bpsRI.setPhone("+62 21 3841195");
        organizationRepository.save(bpsRI);
        
        Organization bpsDKI = new Organization();
        bpsDKI.setName("BPS Provinsi DKI Jakarta");
        bpsDKI.setEmail("bps3100@bps.go.id");
        bpsDKI.setWebsite("https://jakarta.bps.go.id");
        bpsDKI.setType(OrganizationType.PROVINCIAL_NSO);
        bpsDKI.setParentOrganization(bpsRI);
        organizationRepository.save(bpsDKI);
        
        Organization bpsJabar = new Organization();
        bpsJabar.setName("BPS Provinsi Jawa Barat");
        bpsJabar.setEmail("bps3200@bps.go.id");
        bpsJabar.setWebsite("https://jabar.bps.go.id");
        bpsJabar.setType(OrganizationType.PROVINCIAL_NSO);
        bpsJabar.setParentOrganization(bpsRI);
        organizationRepository.save(bpsJabar);
        
        Organization bpsJateng = new Organization();
        bpsJateng.setName("BPS Provinsi Jawa Tengah");
        bpsJateng.setEmail("bps3300@bps.go.id");
        bpsJateng.setWebsite("https://jateng.bps.go.id");
        bpsJateng.setType(OrganizationType.PROVINCIAL_NSO);
        bpsJateng.setParentOrganization(bpsRI);
        organizationRepository.save(bpsJateng);
        
        Organization bpsJatim = new Organization();
        bpsJatim.setName("BPS Provinsi Jawa Timur");
        bpsJatim.setEmail("bps3500@bps.go.id");
        bpsJatim.setWebsite("https://jatim.bps.go.id");
        bpsJatim.setType(OrganizationType.PROVINCIAL_NSO);
        bpsJatim.setParentOrganization(bpsRI);
        organizationRepository.save(bpsJatim);
        
        Organization bpsBali = new Organization();
        bpsBali.setName("BPS Provinsi Bali");
        bpsBali.setEmail("bps5100@bps.go.id");
        bpsBali.setWebsite("https://bali.bps.go.id");
        bpsBali.setType(OrganizationType.PROVINCIAL_NSO);
        bpsBali.setParentOrganization(bpsRI);
        organizationRepository.save(bpsBali);
        
        Organization bpsBandung = new Organization();
        bpsBandung.setName("BPS Kota Bandung");
        bpsBandung.setEmail("bps3273@bps.go.id");
        bpsBandung.setWebsite("https://bandungkota.bps.go.id");
        bpsBandung.setType(OrganizationType.DISTRICT_NSO);
        bpsBandung.setParentOrganization(bpsJabar);
        organizationRepository.save(bpsBandung);
        
        log.info("Created {} organizations", organizationRepository.count());
    }
    
    private void seedDomains() {
        log.info("Seeding Domains...");
        
        // National
        Domain indonesia = new Domain();
        indonesia.setCode("0000");
        indonesia.setName("Indonesia");
        indonesia.setType(DomainType.NATIONAL);
        domainRepository.save(indonesia);
        
        // Provincial
        Domain dki = new Domain();
        dki.setCode("3100");
        dki.setName("DKI Jakarta");
        dki.setType(DomainType.PROVINCIAL);
        dki.setParentDomain(indonesia);
        domainRepository.save(dki);
        
        Domain jabar = new Domain();
        jabar.setCode("3200");
        jabar.setName("Jawa Barat");
        jabar.setType(DomainType.PROVINCIAL);
        jabar.setParentDomain(indonesia);
        domainRepository.save(jabar);
        
        Domain jateng = new Domain();
        jateng.setCode("3300");
        jateng.setName("Jawa Tengah");
        jateng.setType(DomainType.PROVINCIAL);
        jateng.setParentDomain(indonesia);
        domainRepository.save(jateng);
        
        Domain yogya = new Domain();
        yogya.setCode("3400");
        yogya.setName("DI Yogyakarta");
        yogya.setType(DomainType.PROVINCIAL);
        yogya.setParentDomain(indonesia);
        domainRepository.save(yogya);
        
        Domain jatim = new Domain();
        jatim.setCode("3500");
        jatim.setName("Jawa Timur");
        jatim.setType(DomainType.PROVINCIAL);
        jatim.setParentDomain(indonesia);
        domainRepository.save(jatim);
        
        Domain bali = new Domain();
        bali.setCode("5100");
        bali.setName("Bali");
        bali.setType(DomainType.PROVINCIAL);
        bali.setParentDomain(indonesia);
        domainRepository.save(bali);
        
        // District
        Domain bandung = new Domain();
        bandung.setCode("3273");
        bandung.setName("Kota Bandung");
        bandung.setType(DomainType.DISTRICT);
        bandung.setParentDomain(jabar);
        domainRepository.save(bandung);
        
        Domain semarang = new Domain();
        semarang.setCode("3374");
        semarang.setName("Kota Semarang");
        semarang.setType(DomainType.DISTRICT);
        semarang.setParentDomain(jateng);
        domainRepository.save(semarang);
        
        Domain surabaya = new Domain();
        surabaya.setCode("3578");
        surabaya.setName("Kota Surabaya");
        surabaya.setType(DomainType.DISTRICT);
        surabaya.setParentDomain(jatim);
        domainRepository.save(surabaya);
        
        Domain yogyaKota = new Domain();
        yogyaKota.setCode("3471");
        yogyaKota.setName("Kota Yogyakarta");
        yogyaKota.setType(DomainType.DISTRICT);
        yogyaKota.setParentDomain(yogya);
        domainRepository.save(yogyaKota);
        
        log.info("Created {} domains", domainRepository.count());
    }
    
    private void seedSubjectCategories() {
        log.info("Seeding Subject Categories...");
        
        SubjectCategory sosial = new SubjectCategory();
        sosial.setCode("SOC");
        sosial.setName("Sosial");
        sosial.setDescription("Statistik sosial meliputi kependudukan, kesejahteraan rakyat, pendidikan, dan kesehatan");
        subjectRepository.save(sosial);
        
        SubjectCategory ekonomi = new SubjectCategory();
        ekonomi.setCode("ECO");
        ekonomi.setName("Ekonomi");
        ekonomi.setDescription("Statistik ekonomi meliputi perdagangan, keuangan, harga, dan produk domestik bruto");
        subjectRepository.save(ekonomi);
        
        SubjectCategory pertanian = new SubjectCategory();
        pertanian.setCode("AGR");
        pertanian.setName("Pertanian");
        pertanian.setDescription("Statistik pertanian, kehutanan, perikanan, dan perkebunan");
        subjectRepository.save(pertanian);
        
        SubjectCategory industri = new SubjectCategory();
        industri.setCode("IND");
        industri.setName("Industri");
        industri.setDescription("Statistik industri pengolahan dan manufaktur");
        subjectRepository.save(industri);
        
        SubjectCategory perdagangan = new SubjectCategory();
        perdagangan.setCode("TRD");
        perdagangan.setName("Perdagangan");
        perdagangan.setDescription("Statistik perdagangan dalam negeri dan luar negeri");
        subjectRepository.save(perdagangan);
        
        SubjectCategory transportasi = new SubjectCategory();
        transportasi.setCode("TRN");
        transportasi.setName("Transportasi");
        transportasi.setDescription("Statistik transportasi, komunikasi, dan pariwisata");
        subjectRepository.save(transportasi);
        
        SubjectCategory pemerintahan = new SubjectCategory();
        pemerintahan.setCode("GOV");
        pemerintahan.setName("Pemerintahan");
        pemerintahan.setDescription("Statistik administrasi pemerintahan dan keuangan daerah");
        subjectRepository.save(pemerintahan);
        
        SubjectCategory lingkungan = new SubjectCategory();
        lingkungan.setCode("ENV");
        lingkungan.setName("Lingkungan Hidup");
        lingkungan.setDescription("Statistik lingkungan hidup, kehutanan, dan iklim");
        subjectRepository.save(lingkungan);
        
        log.info("Created {} subject categories", subjectRepository.count());
    }
    
    private void seedConcepts() {
        log.info("Seeding Concepts...");
        
        // Kependudukan
        Concept rumahTangga = new Concept();
        rumahTangga.setCode("RT-001");
        rumahTangga.setName("Rumah Tangga");
        rumahTangga.setDefinition("Seseorang atau sekelompok orang yang mendiami sebagian atau seluruh bangunan fisik atau sensus dan umumnya tinggal bersama serta makan dari satu dapur");
        conceptRepository.save(rumahTangga);
        
        Concept krt = new Concept();
        krt.setCode("KRT-001");
        krt.setName("Kepala Rumah Tangga");
        krt.setDefinition("Salah seorang anggota rumah tangga yang bertanggung jawab atas kebutuhan sehari-hari rumah tangga");
        conceptRepository.save(krt);
        
        Concept penduduk = new Concept();
        penduduk.setCode("POP-001");
        penduduk.setName("Penduduk");
        penduduk.setDefinition("Semua orang yang berdomisili di wilayah geografis Indonesia selama 6 bulan atau lebih atau mereka yang berdomisili kurang dari 6 bulan tetapi bertujuan untuk menetap");
        conceptRepository.save(penduduk);
        
        // Ketenagakerjaan
        Concept angkatanKerja = new Concept();
        angkatanKerja.setCode("AK-001");
        angkatanKerja.setName("Angkatan Kerja");
        angkatanKerja.setDefinition("Penduduk usia kerja (15 tahun ke atas) yang bekerja atau punya pekerjaan namun sementara tidak bekerja dan pengangguran");
        conceptRepository.save(angkatanKerja);
        
        Concept bekerja = new Concept();
        bekerja.setCode("BEKERJA-001");
        bekerja.setName("Bekerja");
        bekerja.setDefinition("Kegiatan ekonomi yang dilakukan oleh seseorang dengan maksud memperoleh atau membantu memperoleh pendapatan atau keuntungan paling sedikit 1 jam secara terus menerus selama seminggu yang lalu");
        conceptRepository.save(bekerja);
        
        Concept pengangguran = new Concept();
        pengangguran.setCode("UNEMP-001");
        pengangguran.setName("Pengangguran");
        pengangguran.setDefinition("Penduduk yang tidak bekerja tetapi sedang mencari pekerjaan atau mempersiapkan usaha atau tidak mencari pekerjaan karena merasa tidak mungkin mendapatkan pekerjaan");
        conceptRepository.save(pengangguran);
        
        Concept tpt = new Concept();
        tpt.setCode("TPT-001");
        tpt.setName("Tingkat Pengangguran Terbuka");
        tpt.setDefinition("Persentase jumlah pengangguran terhadap jumlah angkatan kerja");
        tpt.setDataType(VariableDataType.NUMERIC); // Bonus: set dataType
        conceptRepository.save(tpt);
        
        // Ekonomi
        Concept inflasi = new Concept();
        inflasi.setCode("INFL-001");
        inflasi.setName("Inflasi");
        inflasi.setDefinition("Kenaikan harga barang dan jasa secara umum dan terus menerus dalam jangka waktu tertentu");
        inflasi.setDataType(VariableDataType.NUMERIC);
        conceptRepository.save(inflasi);
        
        Concept gdp = new Concept();
        gdp.setCode("GDP-001");
        gdp.setName("Produk Domestik Bruto");
        gdp.setDefinition("Jumlah nilai tambah yang dihasilkan oleh seluruh unit usaha dalam suatu negara tertentu atau merupakan jumlah nilai barang dan jasa akhir yang dihasilkan oleh seluruh unit ekonomi");
        gdp.setDataType(VariableDataType.NUMERIC);
        conceptRepository.save(gdp);
        
        Concept gini = new Concept();
        gini.setCode("GINI-001");
        gini.setName("Rasio Gini");
        gini.setDefinition("Angka yang menunjukkan tingkat ketimpangan distribusi pendapatan secara menyeluruh. Nilai rasio gini berkisar antara 0 sampai 1, semakin mendekati 1 semakin timpang");
        gini.setDataType(VariableDataType.NUMERIC);
        conceptRepository.save(gini);
        
        // Kemiskinan
        Concept garisKemiskinan = new Concept();
        garisKemiskinan.setCode("GK-001");
        garisKemiskinan.setName("Garis Kemiskinan");
        garisKemiskinan.setDefinition("Nilai pengeluaran minimum kebutuhan makanan yang setara dengan 2100 kilokalori per kapita per hari ditambah dengan kebutuhan non makanan");
        garisKemiskinan.setDataType(VariableDataType.NUMERIC);
        conceptRepository.save(garisKemiskinan);
        
        Concept pendudukMiskin = new Concept();
        pendudukMiskin.setCode("POV-001");
        pendudukMiskin.setName("Penduduk Miskin");
        pendudukMiskin.setDefinition("Penduduk yang memiliki rata-rata pengeluaran per kapita per bulan di bawah garis kemiskinan");
        conceptRepository.save(pendudukMiskin);
        
        // Pertanian
        Concept luasLahan = new Concept();
        luasLahan.setCode("LAHAN-001");
        luasLahan.setName("Luas Lahan Pertanian");
        luasLahan.setDefinition("Luas lahan yang diusahakan untuk tanaman pangan, hortikultura, perkebunan, atau peternakan pada saat pencacahan");
        luasLahan.setDataType(VariableDataType.NUMERIC);
        conceptRepository.save(luasLahan);
        
        Concept usahaTani = new Concept();
        usahaTani.setCode("UTP-001");
        usahaTani.setName("Usaha Pertanian");
        usahaTani.setDefinition("Kegiatan yang menghasilkan produk pertanian baik tanaman pangan, hortikultura, perkebunan maupun peternakan yang dikelola oleh rumah tangga pertanian");
        conceptRepository.save(usahaTani);
        
        // Pendidikan
        Concept aps = new Concept();
        aps.setCode("APS-001");
        aps.setName("Angka Partisipasi Sekolah");
        aps.setDefinition("Proporsi anak sekolah pada suatu kelompok umur tertentu terhadap jumlah penduduk pada kelompok umur yang sama dinyatakan dalam persentase");
        aps.setDataType(VariableDataType.NUMERIC);
        conceptRepository.save(aps);
        
        log.info("Created {} concepts", conceptRepository.count());
    }
    
    
    private void seedUsers() {
        log.info("Seeding Users...");
        
        User admin = new User();
        admin.setEmail("admin@bps.go.id");
        admin.setFullname("Administrator BPS");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(UserRole.ADMIN);
        admin.setActive(true);
        userRepository.save(admin);
        
        User mitra = new User();
        mitra.setEmail("mitra@bps.go.id");
        mitra.setFullname("Mitra Statistik");
        mitra.setPassword(passwordEncoder.encode("mitra123"));
        mitra.setRole(UserRole.USER);
        mitra.setActive(true);
        userRepository.save(mitra);
        
        User petugas = new User();
        petugas.setEmail("petugas@bps.go.id");
        petugas.setFullname("Petugas Lapangan");
        petugas.setPassword(passwordEncoder.encode("petugas123"));
        petugas.setRole(UserRole.USER);
        petugas.setActive(true);
        userRepository.save(petugas);
        
        log.info("Created {} users", userRepository.count());
    }
    
    private void seedStatisticalActivities() {
        log.info("Seeding Statistical Activities...");
        
        User admin = userRepository.findByEmail("admin@bps.go.id").orElseThrow();
        Domain indonesia = domainRepository.findByCode("0000").orElseThrow();
        Domain jabar = domainRepository.findByCode("3200").orElseThrow();
        Organization bpsRI = organizationRepository.findByName("BPS RI").orElseThrow();
        Organization bpsJabar = organizationRepository.findByName("BPS Provinsi Jawa Barat").orElseThrow();
        
        SubjectCategory sosial = subjectRepository.findByCode("SOC").orElseThrow();
        SubjectCategory ekonomi = subjectRepository.findByCode("ECO").orElseThrow();
        SubjectCategory pertanian = subjectRepository.findByCode("AGR").orElseThrow();
        SubjectCategory industri = subjectRepository.findByCode("IND").orElseThrow();
        SubjectCategory pemerintahan = subjectRepository.findByCode("GOV").orElseThrow();
        
        // 1. Susenas Maret 2024
        StatisticalActivity susenas = new StatisticalActivity();
        susenas.setActivityCode("ACT-2024-001");
        susenas.setTitle("Survei Sosial Ekonomi Nasional (Susenas) Maret 2024");
        susenas.setYear(2024);
        susenas.setStatus(ActivityStatus.PUBLISHED);
        susenas.setDescription("Susenas adalah survei yang dirancang untuk mengumpulkan data sosial ekonomi rumah tangga yang mencakup kesejahteraan rakyat");
        susenas.setBackground("Susenas telah dilaksanakan sejak 1963 sebagai salah satu kegiatan pengumpulan data BPS yang sangat penting untuk menyediakan data dasar berbagai indikator sosial ekonomi rumah tangga");
        susenas.setObjectives("Menghasilkan data pokok kesejahteraan rakyat terutama yang berkaitan dengan kemiskinan dan ketimpangan");
        susenas.setCollectionMethod(DataCollectionMethod.SURVEY);
        susenas.setFrequency(Frequency.ANNUAL);
        susenas.setCollectionTechniques(Set.of(CollectionTechnique.CAPI));
        susenas.setCoverageArea("Seluruh Indonesia (34 Provinsi, 514 Kabupaten/Kota)");
        susenas.setReferencePeriod("Kondisi sebulan terakhir");
        susenas.setSamplingMethod("Multistage Stratified Random Sampling");
        susenas.setSampleSize(300000);
        susenas.setIsPublic(true);
        susenas.setReleaseDate(LocalDate.of(2024, 7, 1));
        susenas.setDomain(indonesia);
        susenas.setOrganization(bpsRI);
        susenas.setSubjects(Set.of(sosial, ekonomi));
        susenas.setCreatedBy(admin);
        activityRepository.save(susenas);
        
        // 2. Sensus Pertanian 2023
        StatisticalActivity st2023 = new StatisticalActivity();
        st2023.setActivityCode("ACT-2023-001");
        st2023.setTitle("Sensus Pertanian 2023");
        st2023.setYear(2023);
        st2023.setStatus(ActivityStatus.PUBLISHED);
        st2023.setDescription("Sensus Pertanian 2023 (ST2023) adalah sensus lengkap untuk mengumpulkan data struktur ongkos usaha tani dan pendapatan rumah tangga pertanian");
        st2023.setBackground("ST2023 merupakan sensus pertanian ke-6 yang dilaksanakan BPS sejak tahun 1963");
        st2023.setObjectives("Menyediakan data pokok pertanian sebagai dasar perencanaan pembangunan pertanian dan evaluasi program pembangunan");
        st2023.setCollectionMethod(DataCollectionMethod.CENSUS);
        st2023.setFrequency(Frequency.ONE_TIME);
        st2023.setCollectionTechniques(Set.of(CollectionTechnique.CAPI, CollectionTechnique.CAWI));
        st2023.setCoverageArea("Seluruh Indonesia");
        st2023.setReferencePeriod("1 tahun terakhir");
        st2023.setSamplingMethod("Complete Enumeration");
        st2023.setIsPublic(true);
        st2023.setReleaseDate(LocalDate.of(2024, 3, 1));
        st2023.setDomain(indonesia);
        st2023.setOrganization(bpsRI);
        st2023.setSubjects(Set.of(pertanian));
        st2023.setCreatedBy(admin);
        activityRepository.save(st2023);
        
        // 3. Sakernas Februari 2024
        StatisticalActivity sakernas = new StatisticalActivity();
        sakernas.setActivityCode("ACT-2024-002");
        sakernas.setTitle("Survei Angkatan Kerja Nasional (Sakernas) Februari 2024");
        sakernas.setYear(2024);
        sakernas.setStatus(ActivityStatus.PUBLISHED);
        sakernas.setDescription("Survei Angkatan Kerja Nasional (Sakernas) untuk mengukur keadaan ketenagakerjaan seperti tingkat pengangguran terbuka dan tingkat partisipasi angkatan kerja");
        sakernas.setBackground("Sakernas merupakan survei rutin yang dilaksanakan BPS dua kali dalam setahun yaitu Februari dan Agustus");
        sakernas.setObjectives("Menyediakan data ketenagakerjaan secara berkala untuk perencanaan dan evaluasi program ketenagakerjaan");
        sakernas.setCollectionMethod(DataCollectionMethod.SURVEY);
        sakernas.setFrequency(Frequency.BIANNUAL);
        sakernas.setCollectionTechniques(Set.of(CollectionTechnique.CAPI));
        sakernas.setCoverageArea("Seluruh Indonesia");
        sakernas.setReferencePeriod("Seminggu terakhir");
        sakernas.setSamplingMethod("Multistage Stratified Random Sampling");
        sakernas.setSampleSize(200000);
        sakernas.setIsPublic(true);
        sakernas.setReleaseDate(LocalDate.of(2024, 5, 1));
        sakernas.setDomain(indonesia);
        sakernas.setOrganization(bpsRI);
        sakernas.setSubjects(Set.of(sosial));
        sakernas.setCreatedBy(admin);
        activityRepository.save(sakernas);
        
        // 4. Sensus Ekonomi 2026 (DRAFT)
        StatisticalActivity se2026 = new StatisticalActivity();
        se2026.setActivityCode("ACT-2026-001");
        se2026.setTitle("Sensus Ekonomi 2026");
        se2026.setYear(2026);
        se2026.setStatus(ActivityStatus.DRAFT);
        se2026.setDescription("Sensus Ekonomi 2026 (SE2026) untuk pendataan lengkap usaha/perusahaan non-pertanian di seluruh Indonesia");
        se2026.setBackground("SE2026 adalah sensus ekonomi yang ketujuh sejak pelaksanaan pertama kali pada tahun 1986");
        se2026.setObjectives("Menyediakan data dasar untuk penyusunan kerangka sampel survei ekonomi dan updating business register");
        se2026.setCollectionMethod(DataCollectionMethod.CENSUS);
        se2026.setFrequency(Frequency.ONE_TIME);
        se2026.setCollectionTechniques(Set.of(CollectionTechnique.CAPI));
        se2026.setCoverageArea("Seluruh Indonesia");
        se2026.setReferencePeriod("Saat ini");
        se2026.setSamplingMethod("Complete Enumeration");
        se2026.setIsPublic(true);
        se2026.setDomain(indonesia);
        se2026.setOrganization(bpsRI);
        se2026.setSubjects(Set.of(ekonomi, industri));
        se2026.setCreatedBy(admin);
        activityRepository.save(se2026);
        
        // 5. Survei Harga Konsumen Jawa Barat
        StatisticalActivity shk = new StatisticalActivity();
        shk.setActivityCode("ACT-2024-003");
        shk.setTitle("Survei Harga Konsumen Provinsi Jawa Barat 2024");
        shk.setYear(2024);
        shk.setStatus(ActivityStatus.PUBLISHED);
        shk.setDescription("Survei untuk mengumpulkan data harga konsumen di tingkat provinsi sebagai dasar penghitungan inflasi regional");
        shk.setBackground("Survei harga konsumen dilaksanakan secara rutin setiap bulan untuk memantau perkembangan harga");
        shk.setObjectives("Menyediakan data inflasi tingkat provinsi dan komponen pembentuknya");
        shk.setCollectionMethod(DataCollectionMethod.SURVEY);
        shk.setFrequency(Frequency.MONTHLY);
        shk.setCollectionTechniques(Set.of(CollectionTechnique.CAPI));
        shk.setCoverageArea("Provinsi Jawa Barat");
        shk.setReferencePeriod("Bulan berjalan");
        shk.setSamplingMethod("Purposive Sampling");
        shk.setSampleSize(5000);
        shk.setIsPublic(true);
        shk.setReleaseDate(LocalDate.of(2024, 2, 1));
        shk.setDomain(jabar);
        shk.setOrganization(bpsJabar);
        shk.setSubjects(Set.of(ekonomi));
        shk.setCreatedBy(admin);
        activityRepository.save(shk);
        
        // 6. PODES 2024
        StatisticalActivity podes = new StatisticalActivity();
        podes.setActivityCode("ACT-2024-004");
        podes.setTitle("Pendataan Potensi Desa (PODES) 2024");
        podes.setYear(2024);
        podes.setStatus(ActivityStatus.PUBLISHED);
        podes.setDescription("Pendataan Potensi Desa untuk mengumpulkan data potensi desa/kelurahan di seluruh Indonesia");
        podes.setBackground("PODES merupakan sensus yang mencakup seluruh desa dan kelurahan di Indonesia");
        podes.setObjectives("Menyediakan data potensi wilayah tingkat desa/kelurahan meliputi sarana, prasarana, dan kondisi sosial ekonomi");
        podes.setCollectionMethod(DataCollectionMethod.SURVEY);
        podes.setFrequency(Frequency.ONE_TIME);
        podes.setCollectionTechniques(Set.of(CollectionTechnique.PAPI));
        podes.setCoverageArea("Seluruh desa/kelurahan di Indonesia");
        podes.setReferencePeriod("Kondisi saat ini");
        podes.setSamplingMethod("Complete Enumeration");
        podes.setIsPublic(true);
        podes.setReleaseDate(LocalDate.of(2024, 9, 1));
        podes.setDomain(indonesia);
        podes.setOrganization(bpsRI);
        podes.setSubjects(Set.of(pemerintahan, sosial));
        podes.setCreatedBy(admin);
        activityRepository.save(podes);
        
        log.info("Created {} statistical activities", activityRepository.count());
    }
    
    private void seedVariables() {
        log.info("Seeding Variables...");
        
        StatisticalActivity susenas = activityRepository.findByActivityCode("ACT-2024-001").orElseThrow();
        StatisticalActivity sakernas = activityRepository.findByActivityCode("ACT-2024-002").orElseThrow();
        StatisticalActivity st2023 = activityRepository.findByActivityCode("ACT-2023-001").orElseThrow();
        
        Concept rumahTangga = conceptRepository.findByCode("RT-001").orElse(null);
        Concept angkatanKerja = conceptRepository.findByCode("AK-001").orElse(null);
        Concept luasLahan = conceptRepository.findByCode("LAHAN-001").orElse(null);
        
        // Susenas variables
        Variable v1 = new Variable();
        v1.setName("Pengeluaran per kapita sebulan");
        v1.setAlias("r301");
        v1.setDefinition("Total pengeluaran rumah tangga dibagi jumlah anggota rumah tangga dalam sebulan");
        v1.setQuestionText("Berapa total pengeluaran rumah tangga sebulan terakhir?");
        v1.setDataType(VariableDataType.NUMERIC);
        v1.setReferencePeriod("sebulan terakhir");
        v1.setActivity(susenas);
        variableRepository.save(v1);
        
        Variable v2 = new Variable();
        v2.setName("Status Penguasaan Bangunan Tempat Tinggal");
        v2.setAlias("r301a");
        v2.setDefinition("Status penguasaan bangunan tempat tinggal yang ditempati rumah tangga");
        v2.setQuestionText("Status penguasaan bangunan tempat tinggal?");
        v2.setDataType(VariableDataType.CATEGORICAL);
        v2.setReferencePeriod("saat ini");
        v2.setActivity(susenas);
        variableRepository.save(v2);
        
        Variable v3 = new Variable();
        v3.setName("Luas lantai bangunan tempat tinggal");
        v3.setAlias("r303");
        v3.setDefinition("Luas lantai yang ditempati dan dipergunakan untuk keperluan sehari-hari oleh rumah tangga dalam meter persegi");
        v3.setQuestionText("Berapa luas lantai bangunan tempat tinggal (m2)?");
        v3.setDataType(VariableDataType.NUMERIC);
        v3.setReferencePeriod("saat ini");
        v3.setActivity(susenas);
        variableRepository.save(v3);
        
        Variable v4 = new Variable();
        v4.setName("Jenis lantai terluas");
        v4.setAlias("r304");
        v4.setDefinition("Jenis lantai yang terluas digunakan di tempat tinggal");
        v4.setQuestionText("Jenis lantai terluas?");
        v4.setDataType(VariableDataType.CATEGORICAL);
        v4.setActivity(susenas);
        variableRepository.save(v4);
        
        Variable v5 = new Variable();
        v5.setName("Jenis dinding terluas");
        v5.setAlias("r305");
        v5.setDefinition("Jenis bahan bangunan yang terluas untuk dinding luar");
        v5.setQuestionText("Jenis dinding terluas?");
        v5.setDataType(VariableDataType.CATEGORICAL);
        v5.setActivity(susenas);
        variableRepository.save(v5);
        
        Variable v6 = new Variable();
        v6.setName("Sumber air minum");
        v6.setAlias("r306");
        v6.setDefinition("Sumber air yang digunakan untuk minum oleh rumah tangga");
        v6.setQuestionText("Sumber air minum utama?");
        v6.setDataType(VariableDataType.CATEGORICAL);
        v6.setActivity(susenas);
        variableRepository.save(v6);
        
        Variable v7 = new Variable();
        v7.setName("Fasilitas buang air besar");
        v7.setAlias("r307");
        v7.setDefinition("Fasilitas yang digunakan untuk buang air besar oleh anggota rumah tangga");
        v7.setQuestionText("Fasilitas buang air besar yang digunakan?");
        v7.setDataType(VariableDataType.CATEGORICAL);
        v7.setActivity(susenas);
        variableRepository.save(v7);
        
        // Sakernas variables
        Variable v8 = new Variable();
        v8.setName("Kegiatan utama seminggu yang lalu");
        v8.setAlias("b5r1");
        v8.setDefinition("Kegiatan yang paling banyak menyita waktu selama seminggu yang lalu");
        v8.setQuestionText("Kegiatan utama yang dilakukan seminggu yang lalu?");
        v8.setDataType(VariableDataType.CATEGORICAL);
        v8.setReferencePeriod("seminggu terakhir");
        v8.setActivity(sakernas);
        variableRepository.save(v8);
        
        Variable v9 = new Variable();
        v9.setName("Jumlah jam kerja seminggu yang lalu");
        v9.setAlias("b5r2");
        v9.setDefinition("Jumlah jam kerja yang digunakan untuk bekerja selama seminggu yang lalu");
        v9.setQuestionText("Berapa jam bekerja seminggu yang lalu?");
        v9.setDataType(VariableDataType.NUMERIC);
        v9.setReferencePeriod("seminggu terakhir");
        v9.setActivity(sakernas);
        variableRepository.save(v9);
        
        Variable v10 = new Variable();
        v10.setName("Lapangan usaha/bidang pekerjaan utama");
        v10.setAlias("b5r3");
        v10.setDefinition("Bidang kegiatan dari usaha/perusahaan/kantor tempat bekerja");
        v10.setQuestionText("Lapangan usaha dari pekerjaan utama?");
        v10.setDataType(VariableDataType.CATEGORICAL);
        v10.setActivity(sakernas);
        variableRepository.save(v10);
        
        Variable v11 = new Variable();
        v11.setName("Jenis pekerjaan/jabatan");
        v11.setAlias("b5r4");
        v11.setDefinition("Jenis pekerjaan atau jabatan yang dimiliki pada pekerjaan utama");
        v11.setQuestionText("Jenis pekerjaan/jabatan?");
        v11.setDataType(VariableDataType.CATEGORICAL);
        v11.setActivity(sakernas);
        variableRepository.save(v11);
        
        Variable v12 = new Variable();
        v12.setName("Status pekerjaan utama");
        v12.setAlias("b5r5");
        v12.setDefinition("Kedudukan seseorang dalam melakukan pekerjaan");
        v12.setQuestionText("Status pekerjaan utama?");
        v12.setDataType(VariableDataType.CATEGORICAL);
        v12.setActivity(sakernas);
        variableRepository.save(v12);
        
        Variable v13 = new Variable();
        v13.setName("Upah/gaji/pendapatan bersih sebulan");
        v13.setAlias("b5r6");
        v13.setDefinition("Upah atau gaji atau pendapatan bersih yang diterima sebulan dari pekerjaan utama");
        v13.setQuestionText("Berapa upah/gaji/pendapatan bersih sebulan?");
        v13.setDataType(VariableDataType.NUMERIC);
        v13.setReferencePeriod("sebulan terakhir");
        v13.setActivity(sakernas);
        variableRepository.save(v13);
        
        // ST2023 variables
        Variable v14 = new Variable();
        v14.setName("Luas lahan pertanian yang dikuasai");
        v14.setAlias("st101");
        v14.setDefinition("Total luas lahan yang digunakan untuk usaha pertanian dalam hektar");
        v14.setQuestionText("Berapa luas lahan pertanian yang Anda kelola (ha)?");
        v14.setDataType(VariableDataType.NUMERIC);
        v14.setReferencePeriod("saat ini");
        v14.setActivity(st2023);
        variableRepository.save(v14);
        
        Variable v15 = new Variable();
        v15.setName("Jenis tanaman pangan yang diusahakan");
        v15.setAlias("st102");
        v15.setDefinition("Jenis tanaman pangan yang dibudidayakan pada lahan pertanian");
        v15.setQuestionText("Jenis tanaman pangan apa yang diusahakan?");
        v15.setDataType(VariableDataType.CATEGORICAL);
        v15.setActivity(st2023);
        variableRepository.save(v15);
        
        Variable v16 = new Variable();
        v16.setName("Luas panen padi");
        v16.setAlias("st103");
        v16.setDefinition("Luas lahan padi yang dipanen dalam hektar");
        v16.setQuestionText("Berapa luas panen padi (ha)?");
        v16.setDataType(VariableDataType.NUMERIC);
        v16.setReferencePeriod("1 tahun terakhir");
        v16.setActivity(st2023);
        variableRepository.save(v16);
        
        Variable v17 = new Variable();
        v17.setName("Jumlah produksi padi");
        v17.setAlias("st104");
        v17.setDefinition("Jumlah produksi padi yang dihasilkan dalam ton");
        v17.setQuestionText("Berapa jumlah produksi padi (ton)?");
        v17.setDataType(VariableDataType.NUMERIC);
        v17.setReferencePeriod("1 tahun terakhir");
        v17.setActivity(st2023);
        variableRepository.save(v17);
        
        Variable v18 = new Variable();
        v18.setName("Kepemilikan ternak sapi");
        v18.setAlias("st105");
        v18.setDefinition("Jumlah ternak sapi yang dimiliki rumah tangga pertanian");
        v18.setQuestionText("Berapa jumlah ternak sapi yang dimiliki (ekor)?");
        v18.setDataType(VariableDataType.NUMERIC);
        v18.setReferencePeriod("saat ini");
        v18.setActivity(st2023);
        variableRepository.save(v18);
        
        Variable v19 = new Variable();
        v19.setName("Penggunaan alat/mesin pertanian");
        v19.setAlias("st106");
        v19.setDefinition("Jenis alat atau mesin yang digunakan dalam usaha pertanian");
        v19.setQuestionText("Alat/mesin pertanian apa yang digunakan?");
        v19.setDataType(VariableDataType.CATEGORICAL);
        v19.setActivity(st2023);
        variableRepository.save(v19);
        
        Variable v20 = new Variable();
        v20.setName("Sumber modal usaha tani");
        v20.setAlias("st107");
        v20.setDefinition("Sumber utama modal yang digunakan untuk usaha pertanian");
        v20.setQuestionText("Sumber modal usaha tani?");
        v20.setDataType(VariableDataType.CATEGORICAL);
        v20.setActivity(st2023);
        variableRepository.save(v20);
        
        log.info("Created {} variables", variableRepository.count());
    }
    
    private void seedPublications() {
        log.info("Seeding Publications...");
        
        StatisticalActivity susenas = activityRepository.findByActivityCode("ACT-2024-001").orElseThrow();
        StatisticalActivity sakernas = activityRepository.findByActivityCode("ACT-2024-002").orElseThrow();
        StatisticalActivity st2023 = activityRepository.findByActivityCode("ACT-2023-001").orElseThrow();
        StatisticalActivity shk = activityRepository.findByActivityCode("ACT-2024-003").orElseThrow();
        
        // Susenas publications
        Publication pub1 = new Publication();
        pub1.setTitle("Berita Resmi Statistik: Profil Kemiskinan di Indonesia Maret 2024");
        pub1.setIsbn("978-602-438-566-9");
        pub1.setCatalogNumber("1101002");
        pub1.setReleaseDate(LocalDate.of(2024, 7, 1));
        pub1.setDownloadUrl("https://www.bps.go.id/pressrelease/2024/07/01/profil-kemiskinan-2024.pdf");
        pub1.setType(PublicationType.BRS);
        pub1.setLanguage(Language.ID);
        pub1.setActivity(susenas);
        publicationRepository.save(pub1);
        
        Publication pub2 = new Publication();
        pub2.setTitle("Statistik Indonesia 2024");
        pub2.setIsbn("978-602-438-570-6");
        pub2.setCatalogNumber("1101001");
        pub2.setReleaseDate(LocalDate.of(2024, 9, 1));
        pub2.setDownloadUrl("https://www.bps.go.id/publication/2024/statistik-indonesia-2024.pdf");
        pub2.setType(PublicationType.PUBLICATION);
        pub2.setLanguage(Language.ID);
        pub2.setActivity(susenas);
        publicationRepository.save(pub2);
        
        Publication pub3 = new Publication();
        pub3.setTitle("Metadata Susenas 2024");
        pub3.setCatalogNumber("1101002-M");
        pub3.setReleaseDate(LocalDate.of(2024, 7, 15));
        pub3.setDownloadUrl("https://www.bps.go.id/publication/2024/metadata-susenas.pdf");
        pub3.setType(PublicationType.METADATA);
        pub3.setLanguage(Language.ID);
        pub3.setActivity(susenas);
        publicationRepository.save(pub3);
        
        // Sakernas publications
        Publication pub4 = new Publication();
        pub4.setTitle("Berita Resmi Statistik: Keadaan Ketenagakerjaan Indonesia Februari 2024");
        pub4.setIsbn("978-602-438-580-5");
        pub4.setCatalogNumber("3201001");
        pub4.setReleaseDate(LocalDate.of(2024, 5, 1));
        pub4.setDownloadUrl("https://www.bps.go.id/pressrelease/2024/05/01/ketenagakerjaan-feb-2024.pdf");
        pub4.setType(PublicationType.BRS);
        pub4.setLanguage(Language.ID);
        pub4.setActivity(sakernas);
        publicationRepository.save(pub4);
        
        Publication pub5 = new Publication();
        pub5.setTitle("Infografis Angkatan Kerja Indonesia Februari 2024");
        pub5.setReleaseDate(LocalDate.of(2024, 5, 1));
        pub5.setDownloadUrl("https://www.bps.go.id/publication/2024/infografis-sakernas-feb-2024.pdf");
        pub5.setType(PublicationType.INFOGRAPHIC);
        pub5.setLanguage(Language.ID);
        pub5.setActivity(sakernas);
        publicationRepository.save(pub5);
        
        Publication pub6 = new Publication();
        pub6.setTitle("Labor Force Situation in Indonesia February 2024");
        pub6.setIsbn("978-602-438-581-2");
        pub6.setReleaseDate(LocalDate.of(2024, 5, 15));
        pub6.setDownloadUrl("https://www.bps.go.id/publication/2024/labor-force-feb-2024-en.pdf");
        pub6.setType(PublicationType.PUBLICATION);
        pub6.setLanguage(Language.EN);
        pub6.setActivity(sakernas);
        publicationRepository.save(pub6);
        
        // ST2023 publications
        Publication pub7 = new Publication();
        pub7.setTitle("Hasil Sensus Pertanian 2023");
        pub7.setIsbn("978-602-438-600-0");
        pub7.setCatalogNumber("5101001");
        pub7.setReleaseDate(LocalDate.of(2024, 3, 1));
        pub7.setDownloadUrl("https://www.bps.go.id/publication/2024/hasil-st2023.pdf");
        pub7.setType(PublicationType.PUBLICATION);
        pub7.setLanguage(Language.ID);
        pub7.setActivity(st2023);
        publicationRepository.save(pub7);
        
        // SHK publication
        Publication pub8 = new Publication();
        pub8.setTitle("Laporan Bulanan Inflasi Januari 2024 - Provinsi Jawa Barat");
        pub8.setCatalogNumber("7102001");
        pub8.setReleaseDate(LocalDate.of(2024, 2, 1));
        pub8.setDownloadUrl("https://jabar.bps.go.id/publication/2024/inflasi-jan-2024.pdf");
        pub8.setType(PublicationType.BRS);
        pub8.setLanguage(Language.ID);
        pub8.setActivity(shk);
        publicationRepository.save(pub8);
        
        log.info("Created {} publications", publicationRepository.count());
    }
}
