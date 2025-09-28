# FleetShare – Smart Vehicle Sharing Simulator

## نظرة عامة
FleetShare هو نظام محاكاة لمشاركة المركبات، يشمل سيارات، دراجات كهربائية وسكوترات.  
يوفر النظام إمكانية:
- تسجيل المستخدمين وتعيين مستوى الاشتراك (Membership Tier)
- إضافة واستعراض المركبات المتاحة
- بدء وإنهاء الرحلات (Trips) مع حساب السعر باستخدام استراتيجيات تسعير متعددة

---

## المميزات
1. **إدارة المركبات**
    - إضافة المركبات (سيارات، دراجات كهربائية، سكوترات)
    - استعراض المركبات المتاحة

2. **إدارة المستخدمين**
    - تسجيل المستخدمين
    - تعيين مستوى الاشتراك (STANDARD, PREMIUM, VIP)

3. **الرحلات (Trips)**
    - بدء وإنهاء الرحلة
    - تغيير حالة الرحلة (PLANNED, ONGOING, COMPLETED, CANCELED)

4. **التسعير (Pricing)**
    - TimeBasedPricing: حسب مدة الرحلة
    - DistanceBasedPricing: حسب المسافة المقطوعة
    - HybridPricing: الجمع بين الوقت والمسافة
    - SurgeDecorator: زيادة السعر عند الطلب العالي

5. **Repositories**
    - InMemoryUserRepository
    - InMemoryVehicleRepository

6. **ConsoleApp**
    - قائمة تفاعلية لإضافة واستعراض المركبات والمستخدمين وتشغيل الرحلات

---

## الهيكلية (Package Structure)

com.ismail.fleetShare
├─ domain
├─ application
├─ infrastructure
└─ ui


## How to Run
1. Clone repository
2. Build with Maven: `mvn clean install`
3. Run CLI: `java -cp target/classes com.ismail.fleetShare.ui.ConsoleApp`
4. Run tests: `mvn test`
