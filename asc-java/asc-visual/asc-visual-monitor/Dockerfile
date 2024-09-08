#
# Copyright (c) 2024 Leyramu. All rights reserved.
# This project (Digitalization Education), including its source code, documentation, and any associated materials, is the intellectual property of Leyramu. No part of this software may be reproduced, distributed, or transmitted in any form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the copyright owner, Miraitowa_zcx, except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
# For inquiries related to licensing or usage outside the scope of this notice, please contact the copyright holder at 2038322151@qq.com.
# The author disclaims all warranties, express or implied, including but not limited to the warranties of merchantability and fitness for a particular purpose. Under no circumstances shall the author be liable for any special, incidental, indirect, or consequential damages arising from the use of this software.
# By using this project, users acknowledge and agree to abide by these terms and conditions.
#


FROM docker.io/sunrdocker/jdk22-jre-font-openssl-alpine:latest

LABEL maintainer="Miraitowa_zcx"
LABEL description="digEdu-visual-monitor"

COPY target/digEdu-visual-monitor.jar app.jar

EXPOSE 9120

CMD java $JVM_OPTS -jar app.jar >/dev/null 2>&1
